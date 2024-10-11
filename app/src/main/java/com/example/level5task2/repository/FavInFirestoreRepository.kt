package com.example.level5task2.repository

import android.graphics.Movie
import com.example.level5task2.data.api.util.Resource
import com.example.level5task2.data.model.Movies
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class FavInFirestoreRepository {
    private val _firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _favMoviesDocument =
        _firestore.collection("favMovies") // Google generated document ID


    suspend fun addFavMovieToFirestore(movie: Movies): Resource<List<Movies>> {
        // Add a new document with a generated id. "number", "type" and "text" are document fields in Firestore.
        val data = hashMapOf(
            "id" to movie.id,
            "headerIMG" to movie.headerIMG,
            "posterIMG" to movie.posterIMG,
            "title" to movie.title,
            "releaseDate" to movie.releaseDate,
            "rating" to movie.rating,
            "overview" to movie.overview
        )
        try {
            withTimeout(5_000) {
                _favMoviesDocument
                    .add(data)
                    .await()
            }
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured while uploading favMovie to Firestore.")
        }
        return Resource.Success(mutableListOf(movie)) // Return an initialized String object.
    }

    suspend fun getFavMovieFromFirestore(): Resource<List<Movies>> {
        val favMoviesList = arrayListOf<Movies>()
        try {
            withTimeout(5_000) {
                _favMoviesDocument
                    .get().addOnSuccessListener {
                        for (document in it) {
                            val id = document.getLong("id")?.toInt()
                            val headerIMG = document.getString("headerIMG")?:""
                            val posterIMG = document.getString("posterIMG")?:""
                            val title = document.getString("title")?:""
                            val releaseDate = document.getString("releaseDate")?:""
                            val rating = document.getDouble("rating")?: 0.0
                            val overview = document.getString("overview")?:""
                            val movies = Movies(id!!.toInt(),
                                headerIMG, posterIMG, title, releaseDate, rating, overview
                            )
                            favMoviesList.add(movies)
                        }
                    }
                    .await()
            }
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured while retrieving movies data from Firestore.")
        }
        return Resource.Success(favMoviesList)
    }



    suspend fun deleteFavMovieFromFirestore(): Resource<List<Movies>> {
        try {
            withTimeout(5_000) {
                _favMoviesDocument
                    .get().addOnSuccessListener {
                        for (document in it) {
                            document.reference.delete()
                        }
                    }
                    .await()
            }
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured while deleting movie from Firestore.")
        }
        return Resource.Success(emptyList())
    }
}