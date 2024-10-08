package com.example.level5task2.repository

import android.util.Log
import com.example.level5task2.BuildConfig.API_KEY
import com.example.level5task2.data.api.Api
import com.example.level5task2.data.api.ApiService
import com.example.level5task2.data.api.util.Resource
import com.example.level5task2.data.model.MovieResult
import com.example.level5task2.data.model.Movies
import kotlinx.coroutines.withTimeout

class MovieRepository {
    private val _apiService : ApiService = Api.movieClient

    suspend fun getMovies(movie: String): Resource<MovieResult> {
        val response = try {
            withTimeout(15_000) {
                Log.d("MovieRepository", "Making API request with movie: $movie")

                // Pass the movie name to the getMovies call
                _apiService.getMovies(movie, apiKey = API_KEY)

//                Log.d("MovieRepository1", "API call successful, received response: ${apiResponse.results}")
//                apiResponse.results  // Return only the list of movies
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", e.message ?: "No exception message available")
            return Resource.Error("An error occurred while fetching data from the API.")
        }

        return Resource.Success(response)
    }
}