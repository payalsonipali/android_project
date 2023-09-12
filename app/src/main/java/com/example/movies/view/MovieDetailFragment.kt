package com.example.movies.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movies.R
import com.example.movies.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {

    lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        setView()
        return binding.root
    }

    fun setView(){
        val titleWithReleaseDate =
            arguments?.getString("title") + arguments?.getString("releaseData")?.substring(0..3)
        binding.title.text = titleWithReleaseDate
        binding.desc.text = arguments?.getString("description")
        val image = arguments?.getString("movieImg")
        Glide.with(binding.img.context)
            .load("https://image.tmdb.org/t/p/w500${image}")
            .placeholder(R.drawable.movie)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.img)
        val votes = arguments?.getFloat("votes")
        val rating = votes?.div(2.0f)
        if (rating != null) {
            setRatings(rating, binding)
        }
    }

    fun setRatings(rating: Float, binding: FragmentMovieDetailBinding) {

        val stars = arrayOf(
            binding.votes.star1,
            binding.votes.star2,
            binding.votes.star3,
            binding.votes.star4,
            binding.votes.star5
        )

        val filledStars = rating.toInt()
        val fractionalRating = rating - filledStars
        for (i in 0 until filledStars) {
            stars[i].setBackgroundResource(R.drawable.baseline_star_24)
        }
        if (fractionalRating > 0.0f) {
            stars[filledStars].setBackgroundResource(R.drawable.baseline_star_half_24)
        }
    }

}