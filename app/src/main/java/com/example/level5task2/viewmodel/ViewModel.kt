package com.example.level5task2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level5task2.data.api.util.Resource
import com.example.level5task2.data.model.MovieResult
import com.example.level5task2.data.model.Movies
import com.example.level5task2.repository.MovieRepository
import kotlinx.coroutines.launch

class ViewModel : ViewModel(){

    private val _movieRepository = MovieRepository()

    val movieResultResource: LiveData<Resource<MovieResult>>
        get() = _movieResult

    val moviesResource: LiveData<Resource<Movies>>
        get() = _movies


    private val _movieResult: MutableLiveData<Resource<MovieResult>> = MutableLiveData()

    private val _movies: MutableLiveData<Resource<Movies>> = MutableLiveData()


    fun getMovies(movie: String) {
        _movieResult.value = Resource.Loading()

        viewModelScope.launch {
            _movieResult.value = _movieRepository.getMovies(movie)

            Log.d("ViewModel: get the movie", movie)
            Log.d("ViewModel: getMovies", _movieResult.value.toString())
        }
    }
}
