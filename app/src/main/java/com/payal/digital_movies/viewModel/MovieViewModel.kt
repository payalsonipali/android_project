package com.payal.digital_movies.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.payal.digital_movies.model.ApiResponse
import com.payal.digital_movies.model.Content
import com.payal.digital_movies.Repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val context: Context) : ViewModel() {
    private val currentQuery = MutableLiveData<String>()
    private var currentSearchQuery: String = ""

    init {
        loadJsonFromAssets(1)
    }


    val searchResults: LiveData<PagingData<Content>> = currentQuery.switchMap { query ->
        if (query.isNullOrEmpty()) {
            // Return empty data if the query is empty or null
            MutableLiveData(PagingData.empty())
        } else {
            // Perform search and return the results
            MovieRepository().getSearchedMovies(this, query).cachedIn(viewModelScope)

        }
    }

    fun setSearchQuery(query: String) {
        currentQuery.value = query
        currentSearchQuery = query
    }


    var moviePagerFlow: LiveData<PagingData<Content>> =
        MovieRepository().getMoviePage(this).cachedIn(viewModelScope)


    fun loadJsonFromAssets(pageNo: Int): List<Content> {
        var fileName = "CONTENTLISTINGPAGE-PAGE${pageNo}.json"
        Log.d("taggg", "filename : $fileName")
        val inputStream = context.assets.open(fileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        val apiResponse = gson.fromJson(json, ApiResponse::class.java)
        Log.d("taggg", "apiResponse : $apiResponse  \n contentItems : ${apiResponse.page}")
        return apiResponse.page.contentItems.content
    }

    // Add a function to get the current search query
    fun getCurrentSearchQuery(): String {
        return currentSearchQuery
    }

    val allContentItems = mutableListOf<Content>()

    // Iterate through each page and load its data
    fun getAllTitlesForSuggestion() {
        for (pageNo in 1..3) {
            val pageContent = loadJsonFromAssets(pageNo)
            allContentItems.addAll(pageContent)
        }
    }

    fun getSuggestions(query: String): List<String> {
        // Filter titles that start with the query and convert them to a list of unique titles
        return allContentItems
            .filter { it.name.startsWith(query, ignoreCase = true) }
            .map { it.name }
            .distinct()
    }



}