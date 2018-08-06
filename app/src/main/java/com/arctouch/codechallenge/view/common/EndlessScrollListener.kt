package com.arctouch.codechallenge.view.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        var visibleItemCount = 0
        var totalItemCount = 0
        var firstVisibleItem = 0
        with(recyclerView!!) {
            visibleItemCount = childCount
            totalItemCount = layoutManager?.itemCount!!
            firstVisibleItem = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }

        if (isLoading && totalItemCount > previousTotal) {
            isLoading = false
            previousTotal = totalItemCount
        } else if (!isLoading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            isLoading = true
            loadMore()
        }
    }

    abstract fun loadMore()
}