package com.imakeanapp.trackly.tracks.state

import com.imakeanapp.domain.session.model.Session
import com.imakeanapp.domain.tracks.model.Track

/**
 * Represents the different states of [com.imakeanapp.trackly.tracks.view_model.TracksViewModel]
 */
sealed class TracksState {
    data class SessionFetched(val session: Session) : TracksState()
    object ErrorFetchingSession : TracksState()
    object FetchingTracks : TracksState()
    data class TracksFetched(val tracks: List<Track>) : TracksState()
    object TrackChanged : TracksState()
    object ErrorFetchingTracks : TracksState()
    object CurrentScreenSaved : TracksState()
    object ErrorSavingCurrentScreen : TracksState()
    object DateLastVisitedSaved : TracksState()
    object ErrorSavingDateLastVisited : TracksState()
    data class DateLastVisitedFetched(val lastVisited: String) : TracksState()
    object ErrorFetchingDateLastVisited : TracksState()
}