package com.example.movies.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.adapter.LoaderAdapter
import com.example.movies.adapter.MoviePagingAdapter
import com.example.movies.databinding.FragmentMoviesBinding
import com.example.movies.model.MovieDetail
import com.example.movies.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment(),MoviePagingAdapter.OnItemClickListener {

    lateinit var binding: FragmentMoviesBinding
    lateinit var pageviewModel: MovieViewModel
    lateinit var adapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.nowPlaying.setBackgroundResource(R.drawable.rounded_green_bg)

        pageviewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        pageviewModel.getNowPlayingMovies()
        adapter = MoviePagingAdapter(this)
        binding.recycler.adapter = adapter

        binding.nowPlaying.setOnClickListener{
            resetBackground()
            binding.nowPlaying.setBackgroundResource(R.drawable.rounded_green_bg)
            pageviewModel.getNowPlayingMovies()
        }

        binding.popular.setOnClickListener{
            resetBackground()
            binding.popular.setBackgroundResource(R.drawable.rounded_green_bg)
            pageviewModel.getPopularMovies()
        }

        binding.topRated.setOnClickListener{
            resetBackground()
            binding.topRated.setBackgroundResource(R.drawable.rounded_green_bg)
            pageviewModel.getTopRatedMovies()
        }

        binding.upcoming.setOnClickListener{
            resetBackground()
            binding.upcoming.setBackgroundResource(R.drawable.rounded_green_bg)
            pageviewModel.getUpcomingMovies()
        }

        observeViewModel()
        return binding.root
    }

    fun observeViewModel(){
        lifecycleScope.launch{
            val loaderAdapter = LoaderAdapter()
            val headerFooterAdapter = adapter.withLoadStateHeaderAndFooter(
                header = loaderAdapter,
                footer = loaderAdapter
            )
            binding.recycler.adapter = headerFooterAdapter
            binding.recycler.smoothScrollToPosition(0)
            pageviewModel.moviePagerFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        pageviewModel.apply {
            errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
                if (errorMessage.isNotEmpty()) {
                    showSnackBar(binding.root, errorMessage, requireContext())
                    pageviewModel.clearError()
                }
            }
        }
    }

    private fun resetBackground(){
        binding.nowPlaying.setBackgroundResource(R.drawable.rounded_bg)
        binding.popular.setBackgroundResource(R.drawable.rounded_bg)
        binding.topRated.setBackgroundResource(R.drawable.rounded_bg)
        binding.upcoming.setBackgroundResource(R.drawable.rounded_bg)
    }

    private fun showSnackBar(view: View, error:String, context: Context){
        val snackbar = Snackbar.make(view, error, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(context, android.R.color.white))
        snackbar.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
        snackbar.show()
    }

    override fun navigateToMovieDetail(position: Int, movie: MovieDetail) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val destinationFragment = MovieDetailFragment()
        var bundle = Bundle()
        bundle.putString("title", movie.original_title)
        bundle.putString("description", movie.overview)
        bundle.putString("releaseData", movie.release_date)
        bundle.putString("movieImg", movie.poster_path)
        bundle.putFloat("votes", movie.vote_average)
        bundle.putLong("id", movie.id)

        destinationFragment.arguments = bundle
        fragmentTransaction.replace(R.id.homeFram, destinationFragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }
}

