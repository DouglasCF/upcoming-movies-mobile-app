package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.GenreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApi {

    @GET("genre/movie/list")
    fun genres(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Call<GenreResponse>
}