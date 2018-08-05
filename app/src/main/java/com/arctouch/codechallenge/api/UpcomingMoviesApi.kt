package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UpcomingMoviesApi {

    @GET("movie/upcoming")
    fun upcomingMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Long,
            @Query("region") region: String
    ): Call<UpcomingMoviesResponse>
}