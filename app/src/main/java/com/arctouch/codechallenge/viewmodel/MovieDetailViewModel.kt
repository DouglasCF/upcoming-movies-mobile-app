package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.data.remote.MovieRepository

class MovieDetailViewModel : ViewModel() {

    private val movieRepository = MovieRepository()

    fun getMovie(id: Long): LiveData<Movie> {
        return movieRepository.getMovie(id)
    }
}