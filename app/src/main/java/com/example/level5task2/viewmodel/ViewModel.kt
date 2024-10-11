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
import com.example.level5task2.repository.FavInFirestoreRepository
import com.example.level5task2.repository.MovieRepository
import kotlinx.coroutines.launch

class ViewModel : ViewModel(){

    private val _movieRepository = MovieRepository()


    val movieResultResource: LiveData<Resource<MovieResult>>
        get() = _movieResult
    private val _movieResult: MutableLiveData<Resource<MovieResult>> = MutableLiveData()

    val moviesResource: LiveData<Resource<Movies>>
        get() = _movies
    private val _movies: MutableLiveData<Resource<Movies>> = MutableLiveData()


    fun getMovies(movie: String) {
        _movieResult.value = Resource.Loading()

        viewModelScope.launch {
            _movieResult.value = _movieRepository.getMovies(movie)

            Log.d("ViewModel: get the movie", movie)
            Log.d("ViewModel: getMovies", _movieResult.value.toString())
        }
    }

//    fun getMovieById(movieId: Int?): Movies? {
//        return _movieResult.value?.data?.results?.find { it.id == movieId }
//    }
//
//    fun getFavMovieById(movieId: Int?): Movies? {
//        return _movieResult.value?.data?.results?.find { it.id == movieId }
//    }
fun getMovieById(movieId: Int?): Movies? {
    // Try to find the movie in _movieResult first if it's null, try _favMovieResult
    return _movieResult.value?.data?.results?.find { it.id == movieId }
        ?: _favMovieResult.value?.data?.results?.find { it.id == movieId }
}



    /////////////////////// firestore /////////////////////////
    private val _favInFirestoreRepository = FavInFirestoreRepository()

    val favMovieResultResource: LiveData<Resource<MovieResult>>
        get() = _favMovieResult
    private val _favMovieResult: MutableLiveData<Resource<MovieResult>> = MutableLiveData()

    val favMoviesResource: LiveData<Resource<List<Movies>>>
        get() = _favMoviesResource
    private val _favMoviesResource: MutableLiveData<Resource<List<Movies>>> = MutableLiveData(Resource.Empty())



    fun addFavMovieToFirestore(movie: Movies) {

        if (movieIsFav(movie) == false) {
            _favMoviesResource.value = Resource.Loading()

            viewModelScope.launch {
                _favMoviesResource.value =
                    _favInFirestoreRepository.addFavMovieToFirestore(movie)
                Log.d("ViewModel: addFavMovieToFirestore", "$_favMoviesResource is added to firestore")
            }
        }
        else {
            Log.d("ViewModel: addFavMovieToFirestore", "$_favMoviesResource is already added to firestore")
        }


    }

    fun getFavMovieFromFirestore() {
        _favMovieResult.value = Resource.Loading()

        viewModelScope.launch {
            _favMoviesResource.value =
                _favInFirestoreRepository.getFavMovieFromFirestore()
        }
    }

    fun deleteFavMovieFromFirestore(movie: Movies) {
        viewModelScope.launch {
            _favInFirestoreRepository.deleteFavMovieFromFirestore()
        }
    }

    fun movieIsFav(movie: Movies): Boolean {
        return _favMoviesResource.value?.data?.any { it.id == movie.id } == true
    }

}
