package com.payal.digital_movies.view

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.payal.digital_movies.R
import com.payal.digital_movies.adapter.MovieAdapter
import com.payal.digital_movies.adapter.MovieSearchAdapter
import com.payal.digital_movies.databinding.MovieListBinding
import com.payal.digital_movies.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var binding: MovieListBinding
    lateinit var adapter: MovieAdapter
    lateinit var searchAdapter: MovieSearchAdapter
    lateinit var viewModel: MovieViewModel
    private var isSearchViewVisible = false
    private lateinit var suggestionsAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.movie_list)

        binding.recycler.layoutManager = GridLayoutManager(this, calculateSpanCount())

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        initializeAdapters()
        checkLastStateOfScreen()
        observViewModel()
        observSearchView()
        observButtonClick()
        enableFirebaseCrashlytics()
    }

    override fun onBackPressed() {
        if (isSearchViewVisible) {
            // Toggle search view visibility instead of closing the app
            clearSearchText()
            toggleSearchViewVisibility()
        } else {
            super.onBackPressed()
        }
    }

    private fun initializeAdapters() {
        adapter = MovieAdapter()
        searchAdapter = MovieSearchAdapter("")
        suggestionsAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, mutableListOf())
        binding.searchEditText.setAdapter(suggestionsAdapter)
        binding.recycler.adapter = adapter
    }

    private fun observButtonClick() {
        binding.search.setOnClickListener {
            setSearchViewVisibility(true)
            binding.recycler.adapter = searchAdapter
        }

        binding.crossButton.setOnClickListener {
            clearSearchText()
        }
    }

    private fun observSearchView() {
        //it will show data only if 3 characters available as written in doc
        // Handle text changes in the search EditText
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.length >= 3) {
                    setSearchQuery(query)
                    setSuggestions(query)
                } else {
                    searchAdapter.submitData(lifecycle, PagingData.empty())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observViewModel() {
        viewModel.moviePagerFlow.observe(this) {
            adapter.submitData(lifecycle, it)
        }

        viewModel.searchResults.observe(this) {
            searchAdapter.submitData(lifecycle, it)
        }
    }

    private fun toggleSearchViewVisibility() {
        if (isSearchViewVisible) {
            setSearchViewVisibility(false)
            binding.searchEditText.text.clear()
            binding.recycler.adapter = adapter
            viewModel.moviePagerFlow.value?.let { adapter.submitData(lifecycle, it) }
        }
    }

    private fun checkLastStateOfScreen() {
        val savedSearchQuery = viewModel.getCurrentSearchQuery()
        if (savedSearchQuery.isNotEmpty()) {
            setSearchViewVisibility(true)
            setSearchQuery(savedSearchQuery)
            binding.searchEditText.setText(savedSearchQuery)
            binding.recycler.adapter = searchAdapter
        } else {
            binding.recycler.adapter = adapter
        }
    }

    private fun setSuggestions(query: String) {
        viewModel.getAllTitlesForSuggestion()
        val suggestions = viewModel.getSuggestions(query)
        suggestionsAdapter.clear()
        suggestionsAdapter.addAll(suggestions)
        suggestionsAdapter.notifyDataSetChanged()
    }

    private fun setSearchQuery(searchQuery: String) {
        searchAdapter.searchQuery = searchQuery
        viewModel.setSearchQuery(searchQuery)
    }

    private fun clearSearchText() {
        binding.searchEditText.text.clear()
        viewModel.setSearchQuery("")
    }

    private fun setSearchViewVisibility(searchViewVisible: Boolean) {
        if (searchViewVisible) {
            binding.toolbar.visibility = View.GONE
            binding.searchView.visibility = View.VISIBLE
            isSearchViewVisible = true
        } else {
            binding.toolbar.visibility = View.VISIBLE
            binding.searchView.visibility = View.GONE
            isSearchViewVisible = false
        }
    }

    // Calculate the span count based on the device's orientation
    private fun calculateSpanCount(): Int {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        return if (isLandscape) 7 else 3
    }

    private fun enableFirebaseCrashlytics() {
        FirebaseApp.initializeApp(this)
        // Initialize Firebase Crashlytics
        val firebaseCrashlytics = FirebaseCrashlytics.getInstance()
        firebaseCrashlytics.setCrashlyticsCollectionEnabled(true)
    }
}
