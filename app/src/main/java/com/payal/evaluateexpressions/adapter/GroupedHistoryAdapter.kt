package com.payal.evaluateexpressions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payal.evaluateexpressions.databinding.HistoryItemBinding
import com.payal.evaluateexpressions.model.GroupedHistoryItem

class GroupedHistoryAdapter(
    var groupedData: List<GroupedHistoryItem>
) : RecyclerView.Adapter<GroupedHistoryAdapter.GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryItemBinding.inflate(inflater, parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groupedData.get(position))
    }

    override fun getItemCount(): Int {
        return groupedData.size
    }

    inner class GroupViewHolder(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gropItem: GroupedHistoryItem) {
            binding.groupedItem = gropItem
            binding.recyclerView.adapter = HistoryItemAdapter(gropItem.historyItems)
            binding.executePendingBindings()
        }
    }
}
