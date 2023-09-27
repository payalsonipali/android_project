package com.payal.evaluateexpressions.repository

import androidx.lifecycle.LiveData
import com.payal.evaluateexpressions.dao.HistoryItemDao
import com.payal.evaluateexpressions.entity.HistoryItem

class HistoryItemRepository (private val historyItemDao: HistoryItemDao) {

    fun insertHistoryItem(historyItem: HistoryItem) {
        historyItemDao.insert(historyItem)
    }

    fun getAllHistoryItems(): LiveData<List<HistoryItem>> {
        return historyItemDao.getAllHistoryItems()
    }

}