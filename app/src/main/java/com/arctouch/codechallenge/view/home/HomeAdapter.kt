package com.arctouch.codechallenge.view.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.MovieItemBinding
import com.arctouch.codechallenge.model.Movie

class HomeAdapter(private val listener: OnHomeListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnHomeListener {
        fun onClick(movie: Movie)
    }

    companion object {
        private const val ITEM = 0
        private const val PROGRESS = 1
    }

    private val movies = mutableSetOf<Movie?>()

    class ViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: OnHomeListener) {
            binding.movie = movie
            binding.executePendingBindings()

            itemView.setOnClickListener { listener.onClick(movie) }
        }
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> {
                val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder(binding)
            }
            else -> {
                ProgressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false))
            }
        }
    }

    override fun getItemViewType(position: Int) = if (movies.elementAt(position) != null) ITEM else PROGRESS

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> (holder as ViewHolder).bind(movies.elementAt(position)!!, listener)
        }
    }

    fun setData(data: List<Movie>?) {
        if (data == null) {
            movies.add(null)
        } else {
            movies.clear()
            movies.addAll(data)
        }
        notifyDataSetChanged()
    }
}
