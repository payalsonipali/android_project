package com.payal.nasa_image_of_the_day

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.payal.nasa_image_of_the_day.viewmodel.MainActivityViewModel

class DataRefreshWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {

        val apiKey = "QsqATclEpqhgVEMU2TsJo8VJaz8pHmvzELpc7tRS" // Replace with your API key
        val viewModel = MainActivityViewModel(applicationContext)
        viewModel.fetchNASAData(apiKey)
        return Result.success()
    }
}