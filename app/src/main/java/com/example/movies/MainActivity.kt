package com.example.movies

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.view.FavoritesFragment
import com.example.movies.view.MoviesFragment
import com.example.movies.view.ProfileFragment
import com.example.movies.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var viewModel:MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        loadFragment(MoviesFragment())

        binding.bottomNav.setOnItemSelectedListener{
            Log.d("TAGGG","button clicked")
            when(it.itemId){
                R.id.movie -> {
                    Log.d("TAGGG","movie clicked")
                    loadFragment(MoviesFragment())
                    true
                }
                R.id.favorites -> {
                    Log.d("TAGGG","favourites clicked")
                    loadFragment(FavoritesFragment)
                    true
                }
                R.id.profile -> {
                    Log.d("TAGGG","profile clicked")
                    loadFragment(ProfileFragment)
                    true
                }

                else -> false
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.homeFram,fragment)
        transaction.commit()
    }
}
