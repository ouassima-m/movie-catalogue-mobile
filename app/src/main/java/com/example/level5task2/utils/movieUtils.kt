package com.example.level5task2.utils

import com.example.level5task2.data.model.Movies

class MovieUtils {
    companion object {
        // Function to extract poster image URL and title from a Movies object
        fun extractMovieDetails(movie: Movies): Pair<String, String> {
            val posterImage = movie.posterIMG
            val title = movie.title

            return Pair(posterImage, title)
        }
    }
}
