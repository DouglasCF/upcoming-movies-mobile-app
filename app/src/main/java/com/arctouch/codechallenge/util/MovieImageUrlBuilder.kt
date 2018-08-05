package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.data.remote.BaseRemoteRepository

object MovieImageUrlBuilder {

    private const val POSTER_URL = "https://image.tmdb.org/t/p/w154"
    private const val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

    @JvmStatic
    fun buildPosterUrl(posterPath: String?): String {
        return POSTER_URL + posterPath + "?api_key=" + BaseRemoteRepository.API_KEY
    }

    @JvmStatic
    fun buildBackdropUrl(backdropPath: String?): String {
        return BACKDROP_URL + backdropPath + "?api_key=" + BaseRemoteRepository.API_KEY
    }
}
