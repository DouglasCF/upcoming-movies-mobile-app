package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.data.remote.MovieRepository
import com.arctouch.codechallenge.model.Movie

class HomeViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    private var page = 0L

    lateinit var liveData: LiveData<List<Movie>>

    fun getMovies(): LiveData<List<Movie>> {
        liveData = movieRepository.getMovies(++page)
        return liveData
    }
}