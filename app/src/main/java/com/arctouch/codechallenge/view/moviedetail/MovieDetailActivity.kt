package com.arctouch.codechallenge.view.moviedetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.common.AppConstants

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = intent.getStringExtra(AppConstants.EXTRA_TITLE)
        }
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
