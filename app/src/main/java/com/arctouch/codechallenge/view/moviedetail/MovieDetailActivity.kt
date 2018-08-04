package com.arctouch.codechallenge.view.moviedetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.common.AppConstants
import com.arctouch.codechallenge.databinding.ActivityMovieDetailBinding
import com.arctouch.codechallenge.viewmodel.MovieDetailViewModel

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = intent.getStringExtra(AppConstants.EXTRA_TITLE)
        }

        val movieId = intent.getLongExtra(AppConstants.EXTRA_ID, 0)
        val viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        viewModel.getMovie(movieId).observe(this, Observer {
            binding.movie = it
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
