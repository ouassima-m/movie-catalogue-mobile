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
    suspend fun getMovies(movie: String): Resource<List<Movies>> {
        val response = try {
            withTimeout(15_000){
//                val apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5Yzc2N2YyMzY5ZWQ3ZjUxMjViYmE2YzMyYmI2ZDBjOCIsIm5iZiI6MTcyODE2MDYzOS45NTIxNDQsInN1YiI6IjY3MDEzOWNjNmZjNzRlNTc1NmY4NDVmZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.hOF1m6CihgbcA5xM7W1RnsQ_vY2tnncUjL3PoG8GEnI"
//                Log.d("MovieRepository", "Making API request with movie: $movie and apiKey: $apiKey")
//
//                // Making the actual API call with api_key as query parameter
//                val result = _apiService.getMovies(movie, "Bearer $apiKey")
//                Log.d("MovieRepository", "API call successful, received response: $result")
//                result

                  _apiService.getMovies(movie)
//                  Log.d("MovieRepository", "API call successful, received response: ")


            }
        } catch (e: Exception) {
            Log.e("MovieRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred while fetching data from the api.")
        }
        //If everything succeeds we wrap it in a response
        return Resource.Success(response)
    }
}