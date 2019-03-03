package com.imakeanapp.data.tracks.mapper

import com.imakeanapp.data.tracks.repository.local.TrackEntity
import com.imakeanapp.domain.core.Mapper
import com.imakeanapp.domain.tracks.model.Track
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackEntityTrackMapper @Inject constructor() : Mapper<TrackEntity, Track>() {

    override fun mapFrom(from: TrackEntity): Track {
        return Track(
            from.id,
            from.name,
            from.artist,
            from.artworkUrl30,
            from.artworkUrl60,
            from.artworkUrl100,
            from.price,
            from.currency,
            from.genre,
            from.longDescription
        )
    }
}