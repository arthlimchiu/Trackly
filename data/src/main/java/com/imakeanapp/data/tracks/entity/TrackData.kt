package com.imakeanapp.data.tracks.entity

import com.google.gson.annotations.SerializedName

data class TrackData(
    @SerializedName("trackId") var id: Long = 0,
    @SerializedName("trackName") var name: String = "",
    @SerializedName("artistName") var artist: String = "",
    @SerializedName("artworkUrl30") var artworkUrl30: String = "",
    @SerializedName("artworkUrl60") var artworkUrl60: String = "",
    @SerializedName("artworkUrl100") var artworkUrl100: String = "",
    @SerializedName("trackPrice") var price: Double = 0.0,
    @SerializedName("currency") var currency: String = "",
    @SerializedName("primaryGenreName") var genre: String = "",
    @SerializedName("longDescription") var longDescription: String? = ""
)