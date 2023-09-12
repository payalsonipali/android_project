package com.payal.unify.repository

import com.payal.unify.R
import com.payal.unify.model.ApiResponse
import com.payal.unify.model.CardItem

class MockRepository {
    fun getApiResponse(): ApiResponse {
        // Simulate fetching data from a network API
        return ApiResponse(
            expiry = "29-May-2024",
            amountDescription = "UGX 10,000",
            frequency = "As Presented",
            cardItems = listOf(
                CardItem(
                    imageUrl = "https://apptestsoko.s3.ap-south-1.amazonaws.com/assets/a.png",
                    isSelected = true,
                    title = "Airtel Money - XXX897"
                ),
                CardItem(
                    imageUrl = "https://apptestsoko.s3.ap-south-1.amazonaws.com/assets/fp.png",
                    isSelected = false,
                    title = "Flexi Pay - XXX897"
                ),
                CardItem(
                    imageUrl = "https://apptestsoko.s3.ap-south-1.amazonaws.com/assets/m.png",
                    isSelected = false,
                    title = "MTN - XXX897"
                )
            )
        )
    }
}
