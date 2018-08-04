package com.arctouch.codechallenge.view.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseActivity
import com.arctouch.codechallenge.common.AppConstants
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.view.moviedetail.MovieDetailActivity
import com.arctouch.codechallenge.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : BaseActivity(), HomeAdapter.OnHomeListener {

    private lateinit var viewAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupRecyclerView()

        val viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getMovies(1).observe(this, Observer {
            progressBar.visibility = View.GONE
            viewAdapter.setData(it!!)
        })
    }

    private fun setupRecyclerView() {
        viewAdapter = HomeAdapter(this)
        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayoutManager.VERTICAL))
            adapter = viewAdapter
        }
    }

    override fun onClick(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(AppConstants.EXTRA_TITLE, movie.title)
        intent.putExtra(AppConstants.EXTRA_ID, movie.id)
        startActivity(intent)
    }
}
