package com.imakeanapp.trackly.tracks.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imakeanapp.domain.session.model.Session
import com.imakeanapp.domain.session.usecase.GetSession
import com.imakeanapp.domain.session.usecase.SaveSession
import com.imakeanapp.domain.tracks.model.Track
import com.imakeanapp.domain.tracks.usecase.GetTracks
import com.imakeanapp.domain.tracks.usecase.GetTracksResult
import com.imakeanapp.trackly.tracks.state.TracksState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class TracksViewModel(
    private val getTracks: GetTracks,
    private val saveSession: SaveSession,
    private val getSession: GetSession
) : ViewModel() {

    private val state = MutableLiveData<TracksState>()

    private val currentTrack = MutableLiveData<Track>()

    private var tracks: List<Track>? = null

    private val disposables = CompositeDisposable()

    fun getSession() {
        disposables.add(
            getSession.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { session -> state.value = TracksState.SessionFetched(session) },
                    { state.value = TracksState.ErrorFetchingSession }
                )
        )
    }

    fun getTracks() {
        state.value = TracksState.FetchingTracks

        disposables.add(
            getTracks.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        when (it) {
                            is GetTracksResult.OnSuccess -> {
                                tracks = it.tracks
                                state.value = TracksState.TracksFetched(it.tracks)
                            }
                            is GetTracksResult.UnknownError -> {
                                state.value = TracksState.ErrorFetchingTracks
                            }
                        }
                    },
                    {
                        state.value = TracksState.ErrorFetchingTracks
                    }
                )
        )
    }

    fun loadCurrentTrack() {
        tracks?.let { tracks ->
            disposables.add(
                getSession.execute()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { session ->
                        for (track in tracks) {
                            if (track.id == session.lastTrackSelected) {
                                currentTrack.value = track
                                state.value = TracksState.TrackChanged
                                break
                            }
                        }
                    }
            )
        }
    }

    fun setCurrentTrack(track: Track) {
        disposables.add(
            getSession.execute()
                .flatMapCompletable { session ->
                    if (session.lastTrackSelected != track.id) {
                        saveSession.execute(
                            Session(
                                session.lastScreen,
                                track.id,
                                session.dateLastVisited
                            )
                        )
                    } else {
                        Completable.complete()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        currentTrack.value = track
                        state.value = TracksState.TrackChanged
                    },
                    {
                        currentTrack.value = track
                        state.value = TracksState.TrackChanged
                    }
                )
        )
    }

    fun getCurrentTrack(): LiveData<Track> = currentTrack

    fun setCurrentScreen(screen: String) {
        disposables.add(
            getSession.execute()
                .flatMapCompletable { session ->
                    if (!session.lastScreen.contentEquals(screen)) {
                        saveSession.execute(
                            Session(
                                screen,
                                session.lastTrackSelected,
                                session.dateLastVisited
                            )
                        )
                    } else {
                        Completable.complete()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { state.value = TracksState.CurrentScreenSaved },
                    { state.value = TracksState.ErrorSavingCurrentScreen }
                )
        )
    }

    fun getLastVisited() {
        disposables.add(
            getSession.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { session -> state.value = TracksState.DateLastVisitedFetched(session.dateLastVisited) },
                    { state.value = TracksState.ErrorFetchingDateLastVisited }
                )
        )
    }

    fun saveLastVisited(dateTime: String) {
        disposables.add(
            getSession.execute()
                .flatMapCompletable { session ->
                    saveSession.execute(
                        Session(
                            session.lastScreen,
                            session.lastTrackSelected,
                            dateTime
                        )
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { state.value = TracksState.DateLastVisitedSaved },
                    { state.value = TracksState.ErrorSavingDateLastVisited }
                )
        )
    }

    fun state(): LiveData<TracksState> = state

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}