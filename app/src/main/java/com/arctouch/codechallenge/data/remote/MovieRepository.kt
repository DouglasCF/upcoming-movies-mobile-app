package com.arctouch.codechallenge.data.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository : BaseRemoteRepository() {

    fun getMovies(page: Long): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()

        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)
                .enqueue(object : Callback<UpcomingMoviesResponse> {
                    override fun onResponse(call: Call<UpcomingMoviesResponse>?, response: Response<UpcomingMoviesResponse>?) {
                        if (response?.isSuccessful!!) {
                            val movies = response.body()?.results?.map { movie ->
                                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                            }
                            data.value = movies
                        }
                    }

                    override fun onFailure(call: Call<UpcomingMoviesResponse>?, t: Throwable?) {
                    }
                })

        return data
    }

    fun getMovie(id: Long): LiveData<Movie> {
        val data = MutableLiveData<Movie>()

        api.movie(id, TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
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