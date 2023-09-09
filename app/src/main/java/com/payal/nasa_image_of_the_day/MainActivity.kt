package com.payal.nasa_image_of_the_day

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.payal.nasa_image_of_the_day.databinding.ActivityMainLayoutBinding
import com.payal.nasa_image_of_the_day.model.ApiResponse
import com.payal.nasa_image_of_the_day.viewmodel.MainActivityViewModel
import com.payal.nasa_image_of_the_day.viewmodel.MainActivityViewModelFactory

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainLayoutBinding
    lateinit var viewModel: MainActivityViewModel
    val API_KEY = "QsqATclEpqhgVEMU2TsJo8VJaz8pHmvzELpc7tRS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_layout)

        initViewModel()
        fetchData()
        handleRefreshListener()
        ObservViewModel()
    }

    private fun ObservViewModel(){
        viewModel.apply {
            error.observe(this@MainActivity, Observer { data ->
                if (!data.isNullOrEmpty()) {
                    Toast.makeText(this@MainActivity, data, Toast.LENGTH_SHORT).show()
                }
            })

            nasaData.observe(this@MainActivity, Observer { data ->
                // Update UI with the latest NASA data
                binding.titleTextView.text = data.title
                binding.dateTextView.text = data.date
                binding.descriptionTextView.text = data.explanation

                val isVideoContent = viewModel.isVideoContent()
                binding.playButton.visibility = if (isVideoContent) View.VISIBLE else View.GONE

                // Set up click listener for the play button (if needed)
                if (isVideoContent) {
                    loadImgToImgView(data.thumbnail_url)
                    handleVideoPlay(data)
                } else {
                    loadImgToImgView(data.url)
                }
            })
        }


    }
    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            MainActivityViewModelFactory(this)
        ).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = this
    }

    private fun fetchData(){
        // Initialize LiveData with cached data
        viewModel.initCache()

        //fetch data on launch
        viewModel.fetchNASAData(API_KEY)
    }

    private fun handleVideoPlay(data: ApiResponse) {
        binding.playButton.setOnClickListener {
            // Handle video playback here
            val videoUrl = data.url // Use the video URL from your data
            if (!videoUrl.isNullOrEmpty()) {
                // Start video playback (e.g., using ExoPlayer or another library)
                navigateToYoutube(data)
            }
        }
    }

    fun handleRefreshListener(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            // Implement the refresh action here
            refreshContent()
        }
    }
    private fun loadImgToImgView(url: String?) {
        Glide.with(this)
            .load(url)
            .into(binding.imageView)
    }

    private fun navigateToYoutube(data: ApiResponse) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.youtube");
        startActivity(intent)
    }

    private fun refreshContent() {
        // Implement the refresh action here, such as loading the daily image again
        viewModel.fetchNASAData(API_KEY)
        binding.swipeRefreshLayout.isRefreshing = false // Stop the refresh animation when done
    }
}