package com.imakeanapp.domain.tracks.model

data class Track(
    val id: Long,
    val name: String,
    val artist: String,
    val artworkUrl30: String,
    val artworkUrl60: String,
    val artworkUrl100: String,
    val price: Double,
    val currency: String,
    val genre: String,
    val description: String
)