package com.example.level5task2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level5task2.data.api.util.Resource
import com.example.level5task2.data.model.Movies
import com.example.level5task2.repository.MovieRepository
import kotlinx.coroutines.launch

class ViewModel : ViewModel(){

    private val _movieRepository = MovieRepository()

//    val movieResource: LiveData<Resource<Movies>> get()
//        = _movieResource
//
//    private val _movieResource: MutableLiveData<Resource<Movies>>
//        = MutableLiveData(Resource.Empty())

    val movieResource: LiveData<Resource<List<Movies>>> get() = _movieResource

    private val _movieResource: MutableLiveData<Resource<List<Movies>>> = MutableLiveData(Resource.Empty())


    fun getMovies(movie: String) {
        _movieResource.value = Resource.Loading()

        viewModelScope.launch {

            _movieResource.value = _movieRepository.getMovies(movie)
            Log.d("ViewModel: getMovies", _movieResource.value.toString())
        }
    }

//    fun searchMovies(searchQuery: String, movie: String) {
//        if (searchQuery.isBlank()) {
//            _movieResource.value = Resource.Empty()
//        }
//        else {
//            viewModelScope.launch {
//                _movieResource.value = Resource.Loading()
//                _movieResource.value = _movieRepository.getMovies(movie)
//            }
//        }
//    }
}