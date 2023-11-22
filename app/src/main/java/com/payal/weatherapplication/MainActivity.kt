package com.payal.weatherapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.payal.weatherapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: RecyclerViewModel by viewModels()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Initialize RecyclerView and its adapter
        val recyclerView: RecyclerView = binding.recycler
        adapter = RecyclerAdapter(viewModel.weatherData.value ?: emptyList())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        binding.location.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Call the API when text changes
                viewModel.getWeatherData(s.toString(), "LOCATION")
            }
        })

        // Observe LiveData in ViewModel
        viewModel.weatherData.observe(this, Observer { weatherData ->
            // Update UI with weatherData
            Log.d("taggg","weatherData : $weatherData")
            binding.recycler.adapter = RecyclerAdapter(weatherData?: emptyList())
            binding.recycler.adapter?.notifyDataSetChanged()
        })
        // Trigger the API call
        viewModel.getWeatherData("LOCATION", "Date")

    }
}