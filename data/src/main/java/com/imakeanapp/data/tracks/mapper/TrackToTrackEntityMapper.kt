package com.imakeanapp.data.tracks.mapper

import com.imakeanapp.data.tracks.repository.local.TrackEntity
import com.imakeanapp.domain.core.Mapper
import com.imakeanapp.domain.tracks.model.Track
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackToTrackEntityMapper @Inject constructor() : Mapper<Track, TrackEntity>() {

    override fun mapFrom(from: Track): TrackEntity {
        return TrackEntity(
            from.id,
            from.name,
            from.artist,
            from.artworkUrl30,
            from.artworkUrl60,
            from.artworkUrl100,
            from.price,
            from.currency,
            from.genre,
            from.description,
            System.currentTimeMillis()
        )
    }
}