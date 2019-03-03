package com.imakeanapp.data.tracks.repository

import com.imakeanapp.data.connectivity.ConnectivityManagerImpl
import com.imakeanapp.data.core.ItunesApi
import com.imakeanapp.data.tracks.mapper.TrackDataTrackMapper
import com.imakeanapp.data.tracks.repository.local.TracksLocalDataStoreImpl
import com.imakeanapp.domain.tracks.repository.TracksRepository
import com.imakeanapp.domain.tracks.usecase.GetTracksResult
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TracksRepositoryImpl @Inject constructor(
    private val api: ItunesApi,
    private val db: TracksLocalDataStoreImpl,
    private val connectivityManager: ConnectivityManagerImpl,
    private val trackDataMapper: TrackDataTrackMapper
) : TracksRepository {

    override fun getTracks(): Single<GetTracksResult> {
        return getConnectivity()
            .flatMap { hasInternetConnection ->
                if (hasInternetConnection) {
                    getTracksRemotely()
                } else {
                    getTracksFromDb()
                }
            }
            .subscribeOn(Schedulers.io())
    }

    private fun getTracksFromDb(): Single<GetTracksResult> {
        return Single
            .fromCallable { db.getTracks() }
            .map { tracks ->
                if (tracks.isEmpty()) {
                    GetTracksResult.UnknownError
                } else {
                    GetTracksResult.OnSuccess(tracks)
                }
            }
    }

    private fun getTracksRemotely(): Single<GetTracksResult> {
        return api.getTracks()
            .map { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        return@map GetTracksResult.OnSuccess(it.results.map { trackEntity ->
                            trackDataMapper.mapFrom(trackEntity)
                        })
                    }
                    GetTracksResult.UnknownError
                } else {
                    GetTracksResult.UnknownError
                }
            }
            .doOnSuccess { result ->
                if (result is GetTracksResult.OnSuccess) {
                    result.tracks.forEach { db.saveTrack(it) }
                }
            }
    }

    private fun getConnectivity(): Single<Boolean> {
        return Single.just(connectivityManager.hasInternetConnection())
    }
}