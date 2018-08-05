package com.arctouch.codechallenge.data.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.api.GenreApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.GenreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreRepository : BaseRemoteRepository() {

    private val api = retrofit.create(GenreApi::class.java)

    fun getGenres(): LiveData<List<Genre>> {
        val data = MutableLiveData<List<Genre>>()

        api.genres(API_KEY, DEFAULT_LANGUAGE)
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