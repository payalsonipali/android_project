package com.payal.unify.model

data class ApiResponse(
    val expiry: String,
    val amountDescription: String,
    val frequency: String,
    val cardItems:List<CardItem>
)

data class CardItem(
    val imageUrl: String,
    val isSelected: Boolean,
    val title: String
)
