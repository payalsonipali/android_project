package com.example.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movies.R
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.model.MovieDetail

class MoviePagingAdapter(val listener: OnItemClickListener) : PagingDataAdapter<MovieDetail, MoviePagingAdapter.MovieViewHolder>(USER_COMPARATOR) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position);
        if (movie != null) {
            holder.bind(movie, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater,parent, false)
        return MovieViewHolder(binding)
    }

    companion object{
        val USER_COMPARATOR = object : DiffUtil.ItemCallback<MovieDetail>() {
            override fun areItemsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean =
                // User ID serves as unique ID
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean =
                // Compare full contents (note: Java users should call .equals())
                oldItem == newItem
        }
    }

    inner class MovieViewHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieDetail, position: Int) {
            binding.title.text = "${movie.original_title} (${movie.release_date.substring(0..3)})"
            binding.desc.text = movie.overview
            Glide.with(binding.movieImg.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}") // Replace with the base URL and poster path
                .placeholder(R.drawable.movie) // Replace with a placeholder image resource
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.movieImg)
            val rating = movie.vote_average / 2.0f
            setRatings(rating,binding)
            binding.movieImg.setOnClickListener{
                listener.navigateToMovieDetail(position,movie)
            }
            binding.fav.setOnClickListener{
//                listener.addToFavourites()
            }

            binding.executePendingBindings()
        }

        fun setRatings(rating: Float, binding: MovieItemBinding) {
            val stars = arrayOf(
                binding.votes.star1,
                binding.votes.star2,
                binding.votes.star3,
                binding.votes.star4,
                binding.votes.star5
            )

            val filledStars = rating.toInt()
            val fractionalRating = rating-filledStars
            for (i in 0 until filledStars) {
                stars[i].setBackgroundResource(R.drawable.baseline_star_24)
            }
            if (fractionalRating > 0.0f){
                stars[filledStars].setBackgroundResource(R.drawable.baseline_star_half_24)
            }
        }
    }

    interface OnItemClickListener {
        fun navigateToMovieDetail(position: Int, movie: MovieDetail)

//        fun addToFavourites(movie: MovieDetail)

    }

}