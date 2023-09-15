package com.payal.digital_movies.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.payal.digital_movies.databinding.LoaderBinding

class LoaderAdapter: LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        Log.d("taggg","adapter oncreate")
        val inflate = LayoutInflater.from(parent.context)
        val binding = LoaderBinding.inflate(inflate, parent, false)
        return LoaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoaderViewHolder(private val binding:LoaderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(loadState: LoadState){
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.executePendingBindings()
        }
    }

}