package com.example.level5task2.data.api

import android.util.Log
import com.example.level5task2.BuildConfig.API_KEY
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    companion object {

        const val baseUrl = "https://api.themoviedb.org/"

        val movieClient by lazy { createApi(baseUrl) }

        private fun createApi(baseUrl: String): ApiService {

            val client = OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
                .addInterceptor { chain ->
                    val original: Request = chain.request()
                    val request: Request = original.newBuilder()
                        .header("accept", "application/json")
                        .header("Authorization", "Bearer $API_KEY")
                        .method(original.method, original.body)
                        .build()
                    chain.proceed(request)
//                        .header("accept", "application/json")
//                        .header("Authorization", "Bearer $API_KEY")
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}