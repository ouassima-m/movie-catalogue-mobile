package com.example.level5task2.data.api

import com.example.level5task2.data.model.Movies
import retrofit2.http.GET
import retrofit2.http.Query

//interface ApiService {
//    @GET("/search/movie?json=true")
//    suspend fun getMovies(movie: String, apiKey: String): List<Movies>
//}


interface ApiService {
    @GET("search/movie")
    suspend fun getMovies(
        @Query("query") movie: String,
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1
    ): List<Movies>
}