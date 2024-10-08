package com.example.level5task2.data.model

import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Movies>,
    @SerializedName("total_page") val totalPage: Int,
    @SerializedName("total_results") val totalResults: Int,
)
