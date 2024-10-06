package com.example.level5task2.repository

import android.util.Log
import com.example.level5task2.data.api.Api
import com.example.level5task2.data.api.ApiService
import com.example.level5task2.data.api.util.Resource
import com.example.level5task2.data.model.Movies
import kotlinx.coroutines.withTimeout

class MovieRepository {
    private val _apiService : ApiService = Api.movieClient

    //suspend function that calls a suspend function from the numbersApi
    suspend fun getMovies(): Resource<Movies> {
        val response = try {
            withTimeout(5_000){
                _apiService.getMovies()
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred while fetching data from the api.")
        }
        //If everything succeeds we wrap it in a response
        return Resource.Success(response)
    }
}