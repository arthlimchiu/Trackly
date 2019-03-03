package com.imakeanapp.domain.tracks.repository

import com.imakeanapp.domain.tracks.usecase.GetTracksResult
import io.reactivex.Single

/**
 * This interface should be implemented by any class whose responsibility
 * is to manage the sources of tracks either from remote, local persistent storage or
 * any form of Track source.
 */
interface TracksRepository {

    /**
     * Get the tracks from different sources depending on availability
     * and emit a Single.
     *
     * @return a Single that emits the end state that represents
     * the outcome of the process of getting the tracks.
     */
    fun getTracks(): Single<GetTracksResult>
}