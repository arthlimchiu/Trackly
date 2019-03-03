package com.imakeanapp.trackly.tracks.state

import com.imakeanapp.domain.tracks.model.Track

sealed class TrackListListener {
    data class OnTrackClick(val track: Track) : TrackListListener()
}