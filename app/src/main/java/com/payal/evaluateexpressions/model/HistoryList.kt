package com.payal.evaluateexpressions.model

import com.payal.evaluateexpressions.entity.HistoryItem

data class GroupedHistoryItem(
    val date: String,
    val historyItems: List<HistoryItem>,
)