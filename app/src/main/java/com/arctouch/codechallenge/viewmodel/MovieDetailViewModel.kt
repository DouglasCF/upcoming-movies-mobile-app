package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.data.remote.MovieRepository

class MovieDetailViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    private var liveData: LiveData<Movie>? = null

    fun getMovie(id: Long): LiveData<Movie> {
        if (liveData == null) {
            liveData = movieRepository.getMovie(id)
        }
        return liveData!!
    }
}