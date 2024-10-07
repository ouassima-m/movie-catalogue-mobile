package com.example.level5task2.data.api

import com.example.level5task2.BuildConfig.API_KEY
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    companion object {
        // The base url off the api.
        const val baseUrl = "https://api.themoviedb.org/3/"
        const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5Yzc2N2YyMzY5ZWQ3ZjUxMjViYmE2YzMyYmI2ZDBjOCIsIm5iZiI6MTcyODE2MDYzOS45NTIxNDQsInN1YiI6IjY3MDEzOWNjNmZjNzRlNTc1NmY4NDVmZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.hOF1m6CihgbcA5xM7W1RnsQ_vY2tnncUjL3PoG8GEnI"

        val movieClient by lazy { createApi(baseUrl) }

        fun createApi(baseUrl: String): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .header("accept", "application/json")
                        .header("Authorization", "Bearer $API_KEY") // Add your API key here
                        .build()
                    chain.proceed(request)
                }
                .build()

//            val request = Request.Builder()
//                .url("https://api.themoviedb.org/3/search/movie?include_adult=false&language=en-US&page=1")
//                .get()
//                .addHeader("accept", "application/json")
////                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5Yzc2N2YyMzY5ZWQ3ZjUxMjViYmE2YzMyYmI2ZDBjOCIsIm5iZiI6MTcyODE2MDYzOS45NTIxNDQsInN1YiI6IjY3MDEzOWNjNmZjNzRlNTc1NmY4NDVmZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.hOF1m6CihgbcA5xM7W1RnsQ_vY2tnncUjL3PoG8GEnI")
//                .addHeader("Authorization", "Bearer $API_KEY")
//                .build()

//            val response = client.newCall(request).execute()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)


        }

    }
}