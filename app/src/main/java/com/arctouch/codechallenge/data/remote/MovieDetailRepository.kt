package com.arctouch.codechallenge.data.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.api.MovieDetailApi
import com.arctouch.codechallenge.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailRepository:BaseRemoteRepository() {

    private val api = retrofit.create(MovieDetailApi::class.java)

    fun getMovie(id: Long): LiveData<Movie> {
        val data = MutableLiveData<Movie>()

        api.movie(id, API_KEY, DEFAULT_LANGUAGE)
                .enqueue(object : Callback<Movie> {
                    override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                        if (response?.isSuccessful!!) {
                            data.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<Movie>?, t: Throwable?) {
                    }
                })

        return data
    }
}