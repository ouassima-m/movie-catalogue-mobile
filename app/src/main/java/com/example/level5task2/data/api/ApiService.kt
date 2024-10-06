package com.example.level5task2.data.api

import com.example.level5task2.data.model.Movies
import retrofit2.http.GET

interface ApiService {
    @GET("/search/movie?json=true")
    suspend fun getMovies(): List<Movies>
}