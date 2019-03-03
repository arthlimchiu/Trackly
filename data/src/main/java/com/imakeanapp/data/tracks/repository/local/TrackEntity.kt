package com.imakeanapp.data.tracks.repository.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey var id: Long = -1,
    @ColumnInfo(name = "trackName") var name: String = "",
    @ColumnInfo(name = "artistName") var artist: String = "",
    @ColumnInfo(name = "artworkUrl30") var artworkUrl30: String = "",
    @ColumnInfo(name = "artworkUrl60") var artworkUrl60: String = "",
    @ColumnInfo(name = "artworkUrl100") var artworkUrl100: String = "",
    @ColumnInfo(name = "trackPrice") var price: Double = 0.0,
    @ColumnInfo(name = "currency") var currency: String = "",
    @ColumnInfo(name = "primaryGenreName") var genre: String = "",
    @ColumnInfo(name = "longDescription") var longDescription: String = "",
    @ColumnInfo(name = "createdAt") var createdAt: Long = -1
)