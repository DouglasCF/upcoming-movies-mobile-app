package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.data.remote.MovieRepository
import com.arctouch.codechallenge.model.Movie

class HomeViewModel : ViewModel() {

    private val movieRepository = MovieRepository()

    fun getMovies(page: Long): LiveData<List<Movie>> {
        return movieRepository.getMovies(page)
    }
}