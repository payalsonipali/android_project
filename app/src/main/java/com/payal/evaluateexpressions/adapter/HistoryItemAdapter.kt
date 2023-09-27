package com.payal.evaluateexpressions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payal.evaluateexpressions.databinding.HistorySingleExpressionViewBinding
import com.payal.evaluateexpressions.entity.HistoryItem

class HistoryItemAdapter(private val historyItems: List<HistoryItem>) :
RecyclerView.Adapter<HistoryItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistorySingleExpressionViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyItem = historyItems[position]
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int {
        return historyItems.size
    }

    inner class ViewHolder(private val binding: HistorySingleExpressionViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(historyItem: HistoryItem) {
            binding.historyItem = historyItem
            binding.executePendingBindings()
        }
    }
}
