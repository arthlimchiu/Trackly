package com.imakeanapp.data.core

import com.imakeanapp.data.tracks.response.GetTracksResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {

    @GET("search")
    fun getTracks(@Query("term") term: String = "star",
                  @Query("amp;country") country: String = "zzzz",
                  @Query("amp;media") media: String = "movie"): Single<Response<GetTracksResponse>>
}