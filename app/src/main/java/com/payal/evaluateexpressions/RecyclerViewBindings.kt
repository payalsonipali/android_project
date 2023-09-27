package com.payal.evaluateexpressions

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.payal.evaluateexpressions.entity.HistoryItem

@BindingAdapter("app:items")
fun setRecyclerViewItems(recyclerView: RecyclerView, items: List<HistoryItem>?) {}
