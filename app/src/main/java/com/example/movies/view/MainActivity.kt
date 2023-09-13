package com.example.movies.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.movies.R
import com.example.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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
                R.id.profile ->{
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
