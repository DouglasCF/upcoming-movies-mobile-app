package com.arctouch.codechallenge.data.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.GenreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreRepository : BaseRemoteRepository() {

    fun getGenres(): LiveData<List<Genre>> {
        val data = MutableLiveData<List<Genre>>()

        api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .enqueue(object : Callback<GenreResponse> {
                    override fun onResponse(call: Call<GenreResponse>?, response: Response<GenreResponse>?) {
                        if (response?.isSuccessful!!) {
                            val genres = response.body()?.genres
                            Cache.cacheGenres(genres!!)
                            data.value = genres
                        }
                    }

                    override fun onFailure(call: Call<GenreResponse>?, t: Throwable?) {
                    }
                })

        return data
    }
}