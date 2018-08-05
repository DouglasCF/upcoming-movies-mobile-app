package com.arctouch.codechallenge.view.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.common.AppConstants
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.view.common.EndlessScrollListener
import com.arctouch.codechallenge.view.moviedetail.MovieDetailActivity
import com.arctouch.codechallenge.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity(), HomeAdapter.OnHomeListener, SearchView.OnQueryTextListener,
        AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewAdapter: HomeAdapter
    private lateinit var spinnerView: Spinner
    private val scrollListener = object : EndlessScrollListener() {
        override fun loadMore() {
            if (!isFiltering) {
                viewAdapter.setData(null)
                viewModel.getMoreMovies()
            }
        }
    }

    private var isFiltering = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupRecyclerView()
        setupViewModel()
    }

    override fun onDestroy() {
        recyclerView.removeOnScrollListener(scrollListener)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)

        val spinnerItem = menu?.findItem(R.id.action_spinner)
        spinnerView = spinnerItem?.actionView as Spinner
        spinnerView.onItemSelectedListener = this

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        isFiltering = !newText.isNullOrEmpty()
        viewAdapter.filter(newText)
        return true
    }

    private fun setupRecyclerView() {
        viewAdapter = HomeAdapter(this)
        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayoutManager.VERTICAL))
            adapter = viewAdapter
            addOnScrollListener(scrollListener)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getGenres().observe(this, Observer {
            observeMovies()
        })
    }

    private fun setGenres(genres: List<Genre>) {
        val array = mutableListOf<String>()
        array.add(getString(R.string.all_genres))
        array.addAll(genres.map { it.name })

        val adapter = ArrayAdapter<String>(this, R.layout.item_genre_spinner, array)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerView.adapter = adapter
    }

    private fun observeMovies() {
        viewModel.getMovies().observe(this, Observer {
            progressBar.visibility = View.GONE
            viewAdapter.setData(it!!)
            setGenres(Cache.genres)
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var selectedItem = parent?.getItemAtPosition(position).toString()
        if (selectedItem == getString(R.string.all_genres)) {
            selectedItem = ""
            isFiltering = false
        } else {
            isFiltering = true
        }
        viewAdapter.filterGenre(selectedItem)
    }

    override fun onClick(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(AppConstants.EXTRA_TITLE, movie.title)
        intent.putExtra(AppConstants.EXTRA_ID, movie.id)
        startActivity(intent)
    }

    override fun showEmptyFilter() {
        emptyFilterText.visibility = View.VISIBLE
    }

    override fun removeEmptyFilter() {
        emptyFilterText.visibility = View.GONE
    }
}
