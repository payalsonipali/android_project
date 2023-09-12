package com.payal.unify.repository

import com.google.gson.Gson
import com.payal.unify.model.ApiResponse

class MockRepository {

    private val json = """
        {
            "expiry": "29-May-2024",
            "amountDescription": "UGX 10,000",
            "frequency": "As Presented",
            "cardItems": [
                {
                    "imageUrl": "https://apptestsoko.s3.ap-south-1.amazonaws.com/assets/a.png",
                    "isSelected": true,
                    "title": "Airtel Money - XXX897"
                },
                {
                    "imageUrl": "https://apptestsoko.s3.ap-south-1.amazonaws.com/assets/fp.png",
                    "isSelected": false,
                    "title": "Flexi Pay - XXX897"
                },
                {
                    "imageUrl": "https://apptestsoko.s3.ap-south-1.amazonaws.com/assets/m.png",
                    "isSelected": false,
                    "title": "MTN - XXX897"
                }
            ]
        }
    """.trimIndent()

    private val gson = Gson()

    fun getApiResponse(): ApiResponse {
        return gson.fromJson(json, ApiResponse::class.java)
    }
}
