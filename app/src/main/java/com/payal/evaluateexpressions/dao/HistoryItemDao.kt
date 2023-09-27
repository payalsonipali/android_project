package com.payal.evaluateexpressions.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.payal.evaluateexpressions.entity.HistoryItem

@Dao
interface HistoryItemDao {
    @Insert
    fun insert(historyItem: HistoryItem)

    @Query("SELECT * FROM history_items ORDER BY submissionDate DESC")
    fun getAllHistoryItems(): LiveData<List<HistoryItem>>
}