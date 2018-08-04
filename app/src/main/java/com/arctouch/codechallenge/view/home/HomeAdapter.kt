package com.arctouch.codechallenge.view.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arctouch.codechallenge.databinding.MovieItemBinding
import com.arctouch.codechallenge.model.Movie

class HomeAdapter(private val listener: OnHomeListener) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    interface OnHomeListener {
        fun onClick(movie: Movie)
    }

    private val movies = mutableListOf<Movie>()

    class ViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: OnHomeListener) {
            binding.movie = movie
            binding.executePendingBindings()

            itemView.setOnClickListener { listener.onClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position], listener)

    fun setData(data: List<Movie>) {
        movies.clear()
        movies.addAll(data)
        notifyDataSetChanged()
    }
}
