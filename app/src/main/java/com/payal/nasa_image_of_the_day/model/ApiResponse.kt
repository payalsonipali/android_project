package com.payal.nasa_image_of_the_day.model

data class ApiResponse(
    val title: String?,
    val date: String?,
    val explanation: String?,
    val url: String?,
    val media_type: String?,
    val hdurl: String?,
    val serviceVersion: String?,
)
