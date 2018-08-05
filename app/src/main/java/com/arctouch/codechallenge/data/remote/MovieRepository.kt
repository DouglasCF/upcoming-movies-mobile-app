package com.arctouch.codechallenge.data.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.api.UpcomingMoviesApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository : BaseRemoteRepository() {

    private val api = retrofit.create(UpcomingMoviesApi::class.java)

    private val cache = LinkedHashMap<Long, List<Movie>>()

    fun getMovies(page: Long): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()

        if (cache.containsKey(page)) {
            data.value = getMoviesFromCache(page)
        } else {
            api.upcomingMovies(API_KEY, DEFAULT_LANGUAGE, page, DEFAULT_REGION)
                    .enqueue(object : Callback<UpcomingMoviesResponse> {
                        override fun onResponse(call: Call<UpcomingMoviesResponse>?, response: Response<UpcomingMoviesResponse>?) {
                            if (response?.isSuccessful!!) {
                                val results = response.body()?.results?.map { movie ->
                                    movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                                }
                                cache[page] = results!!
                                data.value = getMoviesFromCache(page)
                            }
                        }

                        override fun onFailure(call: Call<UpcomingMoviesResponse>?, t: Throwable?) {
                        }
                    })
        }

        return data
    }

    private fun getMoviesFromCache(page:Long) :List<Movie>{
        val movies = mutableListOf<Movie>()
        cache.filter { it.key <= page }.forEach { movies.addAll(it.value) }
        return movies
    }
}