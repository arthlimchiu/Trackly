package com.imakeanapp.data.tracks.repository.local

import com.imakeanapp.data.tracks.mapper.TrackEntityTrackMapper
import com.imakeanapp.data.tracks.mapper.TrackToTrackEntityMapper
import com.imakeanapp.domain.tracks.model.Track
import com.imakeanapp.domain.tracks.repository.TracksLocalDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TracksLocalDataStoreImpl @Inject constructor(
    private val db: TracklyDatabase,
    private val trackEntityMapper: TrackToTrackEntityMapper,
    private val trackMapper: TrackEntityTrackMapper
) : TracksLocalDataStore {

    override fun getTracks(): List<Track> {
        return db.trackDao().getTracks().map { entity -> trackMapper.mapFrom(entity) }
    }

    override fun saveTrack(track: Track) {
        db.trackDao().insert(trackEntityMapper.mapFrom(track))
    }
}