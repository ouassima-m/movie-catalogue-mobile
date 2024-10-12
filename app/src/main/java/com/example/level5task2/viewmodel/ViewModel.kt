package com.example.level5task2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    return _favMoviesResource.value?.data?.find { it.id == movieId }
        ?: _movieResult.value?.data?.results?.find { it.id == movieId }
}





    /////////////////////// firestore /////////////////////////
    private val _favInFirestoreRepository = FavInFirestoreRepository()

    val favMovieResultResource: LiveData<Resource<MovieResult>>
        get() = _favMovieResult
    private val _favMovieResult: MutableLiveData<Resource<MovieResult>> = MutableLiveData()

    val favMoviesResource: LiveData<Resource<List<Movies>>>
        get() = _favMoviesResource
    private val _favMoviesResource: MutableLiveData<Resource<List<Movies>>> = MutableLiveData(Resource.Empty())


    val movieIsFavResource: LiveData<Boolean> get() = _movieIsFav
    private val _movieIsFav: MutableLiveData<Boolean> = MutableLiveData()


    fun addFavMovieToFirestore(movie: Movies) {

        if (movieIsFav(movie) == false) {
            _favMoviesResource.value = Resource.Loading()

            viewModelScope.launch {
                _favMoviesResource.value =
                    _favInFirestoreRepository.addFavMovieToFirestore(movie)
                Log.d("ViewModel: addFavMovieToFirestore", "$_favMoviesResource is added to firestore")
//                updateMovieIsFavStatus(movie)
                _movieIsFav.value = true

            }
        }
        else {
            Log.d("ViewModel: addFavMovieToFirestore", "$_favMoviesResource is already added to firestore deleting it now")
            deleteFavMovieFromFirestore(movie)
        }
    }

    fun updateMovieIsFavStatus(movie: Movies?) {
        _movieIsFav.value = movieIsFav(movie)
    }

    fun getFavMovieFromFirestore() {
        Log.d("......ViewModel: favMoviesResource", _favMoviesResource.value.toString())

        _favMovieResult.value = Resource.Loading()

        viewModelScope.launch {
            _favMoviesResource.value = _favInFirestoreRepository.getFavMovieFromFirestore()

        }
    }

    fun deleteFavMovieFromFirestore(movie: Movies) {
        viewModelScope.launch {
            _favInFirestoreRepository.deleteFavMovieFromFirestore(movie)
            getFavMovieFromFirestore()
//            updateMovieIsFavStatus(movie)
            _movieIsFav.value = false

        }
    }

    fun movieIsFav(movie: Movies?): Boolean {
        return _favMoviesResource.value?.data?.any { it.id == movie?.id } == true
    }
}
