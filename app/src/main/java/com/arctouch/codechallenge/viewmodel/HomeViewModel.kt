package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.data.remote.MovieRepository
import com.arctouch.codechallenge.model.Movie

class HomeViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    private val pageLiveData = MutableLiveData<Long>()
    private var liveData: LiveData<List<Movie>>? = null

    init {
        pageLiveData.value = 1L
    }

    fun getMovies(): LiveData<List<Movie>> {
        if (liveData == null) {
            liveData = Transformations.switchMap(pageLiveData) {
                movieRepository.getMovies(it!!)
            }
        }
        return liveData!!
    }

    fun getMoreMovies() {
        pageLiveData.value = pageLiveData.value?.plus(1)
    }
}