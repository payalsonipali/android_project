package com.payal.digital_movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.payal.digital_movies.model.Content
import com.payal.digital_movies.R
import com.payal.digital_movies.const.imageResourceMap
import com.payal.digital_movies.databinding.MovieItemBinding

class MovieAdapter :
    PagingDataAdapter<Content, MovieAdapter.MovieViewHolder>(USER_COMPARATOR) {
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position);
        if (movie != null) {
            holder.bind(movie, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    companion object {
        val USER_COMPARATOR = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem == newItem
        }
    }

    inner class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Content, position: Int) {
            // Highlight matching text
            binding.title.text = movie.name
            binding.img.setImageResource(
                imageResourceMap[movie.posterImage] ?: R.drawable.placeholder_for_missing_posters
            )
            binding.executePendingBindings()
        }
    }

}