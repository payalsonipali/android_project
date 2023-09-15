package com.payal.digital_movies.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.payal.digital_movies.model.Content
import com.payal.digital_movies.R
import com.payal.digital_movies.databinding.MovieItemBinding

class MovieSearchAdapter(var searchQuery:String) : PagingDataAdapter<Content, MovieSearchAdapter.MovieViewHolder>(USER_COMPARATOR) {

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
        val USER_COMPARATOR = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean =
                // User ID serves as unique ID
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean =
                // Compare full contents (note: Java users should call .equals())
                oldItem == newItem
        }
    }

    inner class MovieViewHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Content, position: Int) {
            // Highlight matching text
            Log.d("taggg","search query : ${searchQuery}")
            if (searchQuery.isNotEmpty()) {
                Log.d("taggg","search query not empty : ${searchQuery}")
                binding.title.text = highlightSearchQuery(movie.name, searchQuery,
                    ContextCompat.getColor(binding.root.context, R.color.highlight_color))
            } else{
                binding.title.text = movie.name
            }
            binding.img.setImageResource(imageResourceMap[movie.posterImage]?: R.drawable.placeholder_for_missing_posters)
            binding.executePendingBindings()
        }
    }

    private fun highlightSearchQuery(text: String, searchQuery: String, @ColorInt highlightColor: Int): SpannableString {
        val spannableString = SpannableString(text)
        val startIndex = text.indexOf(searchQuery, ignoreCase = true)
        if (startIndex != -1) {
            val endIndex = startIndex + searchQuery.length
            spannableString.setSpan(
                ForegroundColorSpan(highlightColor),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannableString
    }

    val imageResourceMap = hashMapOf(
        "poster1.jpg" to R.drawable.poster1,
        "poster2.jpg" to R.drawable.poster2,
        "poster3.jpg" to R.drawable.poster3,
        "poster4.jpg" to R.drawable.poster4,
        "poster5.jpg" to R.drawable.poster5,
        "poster6.jpg" to R.drawable.poster6,
        "poster7.jpg" to R.drawable.poster7,
        "poster8.jpg" to R.drawable.poster8,
        "poster9.jpg" to R.drawable.poster9,
        // Add more mappings as needed for other images
    )

}