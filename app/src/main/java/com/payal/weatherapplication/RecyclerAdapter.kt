package com.payal.weatherapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.payal.weatherapplication.databinding.RecyclerItemsBinding

class RecyclerAdapter(val list:List<Forecastday>) : RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RecyclerItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ItemViewHolder(private val binding: RecyclerItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Forecastday) {
            binding.location.text = item.dateEpoch.toString()

            Glide.with(itemView)
                .load("https://"+item.day.condition.icon) // Assuming item.icon is the URL or resource ID for the image
                .into(binding.img)
        }
    }
}