package com.payal.evaluateexpressions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payal.evaluateexpressions.entity.HistoryItem
import com.payal.evaluateexpressions.model.GroupedHistoryItem
import com.payal.evaluateexpressions.repository.HistoryItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryViewModel(private val repository: HistoryItemRepository) : ViewModel() {

    val historyItems: LiveData<List<HistoryItem>> = repository.getAllHistoryItems()

    fun getHistoryItemsGroupedByDate(): LiveData<List<GroupedHistoryItem>> {

        val historyItemsLiveData = repository.getAllHistoryItems()

        val groupedHistoryItemsLiveData: LiveData<List<GroupedHistoryItem>> =
            Transformations.map(historyItemsLiveData) { historyItems ->
                val groupedItems = historyItems.groupBy { item ->
                    // Convert the timestamp to a formatted date (e.g., "yyyy-MM-dd")
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    dateFormat.format(item.submissionDate) // Assuming submissionDate is of type Long
                }
                // Create a list of GroupedHistoryItem objects
                groupedItems.map { (date, items) ->
                    GroupedHistoryItem(date, items)
                }
            }
        return groupedHistoryItemsLiveData
    }
    fun insertHistoryItem(historyItem: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertHistoryItem(historyItem)
        }
    }

}