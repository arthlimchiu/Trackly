package com.imakeanapp.domain.tracks.usecase

import com.imakeanapp.domain.core.SingleUseCase
import com.imakeanapp.domain.tracks.model.Track
import com.imakeanapp.domain.tracks.repository.TracksRepository
import io.reactivex.Single

/**
 * A single unit of action that gets the tracks from an instance
 * of TracksRepository
 */
class GetTracks(private val repository: TracksRepository): SingleUseCase<GetTracksResult> {
    override fun execute(): Single<GetTracksResult> = repository.getTracks()
}

/**
 * Used to represent the state of the outcome of the action of getting the tracks
 * from an instance of TracksRepository
 */
sealed class GetTracksResult {
    data class OnSuccess(val tracks: List<Track>) : GetTracksResult()
    object UnknownError : GetTracksResult()
}