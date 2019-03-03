package com.imakeanapp.data.tracks.response

import com.google.gson.annotations.SerializedName
import com.imakeanapp.data.tracks.entity.TrackData

data class GetTracksResponse(
    @SerializedName("resultCount")
    val count: Long,
    @SerializedName("results")
    val results: List<TrackData>
)