package com.imakeanapp.domain.tracks.repository

import com.imakeanapp.domain.tracks.model.Track

/**
 * This interface is used for classes dealing with local persistence specifically
 * for tracks.
 */
interface TracksLocalDataStore {

    /**
     * Get the tracks either from a db or some form of
     * persistent storage.
     *
     * @return a list of tracks
     */
    fun getTracks(): List<Track>


    /**
     * Save the track to a local db or some form of
     * persistent storage.
     *
     * @param track the track to be saved locally
     */
    fun saveTrack(track: Track)
}