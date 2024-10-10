package com.example.level5task2.data.model

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class Movies(
    @SerializedName("id") val id : Int,
    @SerializedName("backdrop_path") val headerIMG : String,
    @SerializedName("poster_path") val posterIMG : String,
    @SerializedName("original_title") val title : String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("popularity") val rating : Double,
    @SerializedName("overview") val overview  : String,
)
