package com.example.level5task2.data.api

import com.example.level5task2.data.model.MovieResult
import com.example.level5task2.data.model.Movies
import com.google.android.gms.common.api.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Retrofit gebruikt deze interface om HTTP requests te maken
interface ApiService {
    @GET("3/search/movie?")
    suspend fun getMovies(
        @Query("query") movie: String,
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1,
//        @Query("api_key") apiKey: String
    ): MovieResult //returns
}