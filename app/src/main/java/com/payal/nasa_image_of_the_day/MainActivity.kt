package com.payal.nasa_image_of_the_day

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.payal.nasa_image_of_the_day.databinding.ActivityMainLayoutBinding
import com.payal.nasa_image_of_the_day.viewmodel.MainActivityViewModel
import com.payal.nasa_image_of_the_day.viewmodel.MainActivityViewModelFactory
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    lateinit var binding:ActivityMainLayoutBinding
    lateinit var viewModel:MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_layout)
        Log.d("tagggg","33333333333333333333333333")

        viewModel = ViewModelProvider(this,MainActivityViewModelFactory(this)).get(MainActivityViewModel::class.java)
        Log.d("tagggg","222222222222222222222")
        binding.lifecycleOwner = this

        val API_KEY = "QsqATclEpqhgVEMU2TsJo8VJaz8pHmvzELpc7tRS"
        viewModel.fetchNASAData(API_KEY)

        // Initialize LiveData with cached data
        viewModel.initCache()

        viewModel.nasaData.observe(this, Observer { data ->
            // Update UI with the latest NASA data
            binding.titleTextView.text = data.title
            binding.dateTextView.text = data.date
            binding.descriptionTextView.text = data.explanation

            // Load image using Glide into ImageView
            Glide.with(this)
                .load(data.url)
                .into(binding.imageView)
        })

        // Schedule WorkManager task
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val dataRefreshWorkRequest = PeriodicWorkRequest.Builder(
            DataRefreshWorker::class.java,
            1, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "NASADataRefreshWork",
            ExistingPeriodicWorkPolicy.KEEP,
            dataRefreshWorkRequest
        )
    }
}