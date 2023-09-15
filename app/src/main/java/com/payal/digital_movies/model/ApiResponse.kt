package com.payal.digital_movies.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val page: Page
)

data class Page(
    val title: String,

    @SerializedName("total-content-items")
    val totalContentItems: String,

    @SerializedName("page-num")
    val pageNum: String,

    @SerializedName("page-size")
    val pageSize: String,

    @SerializedName("content-items")
    val contentItems: ContentItems
)

data class ContentItems(
    val content: List<Content>
)

data class Content(
    val name: String,

    @SerializedName("poster-image")
    val posterImage: String
)
