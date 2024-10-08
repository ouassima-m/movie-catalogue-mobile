package com.example.level5task2.ui.theme.screens

import android.graphics.Movie
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.level5task2.R
import com.example.level5task2.data.api.util.Resource
import com.example.level5task2.viewmodel.ViewModel
import androidx.compose.runtime.livedata.observeAsState
import coil.compose.rememberAsyncImagePainter
import com.example.level5task2.data.model.MovieResult
import com.example.level5task2.data.model.Movies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    navController: NavHostController,
    viewModel: ViewModel
){
    val movieResultResource : Resource<MovieResult>? by viewModel.movieResultResource.observeAsState()
    val moviesResource : Resource<Movies>? by viewModel.moviesResource.observeAsState()



    val posterIMGList = when (moviesResource) {
        is Resource.Success -> moviesResource?.data?.posterIMG
        is Resource.Error -> movieResultResource?.message
        is Resource.Loading -> stringResource(R.string.loading_text)
        is Resource.Empty -> stringResource(id = R.string.maybe_none_empty)
        else -> stringResource(R.string.something_wrong_state)
    }
    Log.d("movieResource here", moviesResource.toString())

    Scaffold (
        content = { innerPadding ->
            Column (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
//                viewModel.getMovies()
            ){
                Row (){
                    SearchView (
                        searchTMDB = {
                            movie: String ->
                            viewModel.getMovies(movie)
                            Log.d("overview: searchTMDB", movie)
                        },
                        posterIMGList = posterIMGList

                    )
                    }
                    FavButton(navController = navController)

                Display(
                    viewModel = viewModel,
                    moviesResource = moviesResource,
                    movieResultResource = movieResultResource,
                    isLoading = moviesResource is Resource.Loading,
                    hasNoResults = moviesResource is Resource.Empty,
                )
            }

        }
    )
}
@Composable
fun Display(
    viewModel: ViewModel,
    moviesResource: Resource<Movies>?,
    movieResultResource: Resource<MovieResult>?,
    isLoading: Boolean,
    hasNoResults: Boolean
) {
    Log.d("Display: moviesResource", moviesResource.toString())
    Log.d("Display: movieResultResource", movieResultResource.toString())

    if (isLoading) {
        Log.d("Display: movieResult is loading", movieResultResource?.data?.results.toString())

        Text(text = "Loading...", modifier = Modifier.padding(16.dp), fontSize = 18.sp)
    } else if (hasNoResults || (movieResultResource?.data?.results.isNullOrEmpty())) {
        Log.d("Display: movieResult has no results", movieResultResource?.data?.results.toString())

        Text(text = "Maybe none?", modifier = Modifier.padding(16.dp), fontSize = 18.sp)
    } else {
        Log.d("Display: movieResult else", movieResultResource?.data?.results.toString())

        movieResultResource?.data?.results?.let { movies ->
            LazyColumn {
                items(movies) { movie ->
                    MovieCard(movie)
                }
            }
        }
    }
}

//    if (isLoading) {
//        Log.d("Dispaly: movieResult is loading", movieResultResource?.data?.results.toString())
//        Text(text = "Loading...", modifier = Modifier.padding(16.dp), fontSize = 18.sp)
//
//    } else if (hasNoResults || movieResultResource?.data?.results.isNullOrEmpty()) {
//        Log.d("Dispaly: movieResult has no results", movieResultResource?.data?.results.toString())
//        Text(text = "Maybe none?", modifier = Modifier.padding(16.dp), fontSize = 18.sp)
//
//    } else {
//        Log.d("Dispaly: movieResult else", movieResultResource?.data?.results.toString())
//        movieResultResource?.data?.results?.let { movies ->
//
//            LazyColumn {
//                items(movies) { movie ->
//                    MovieCard(movie)
//                }
//            }
//        }
//    }
//}




@Composable
fun MovieCard(movie: Movies) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model =
                //todo
                "https://image.tmdb.org/t/p/w500${movie.posterIMG}"),
                contentDescription = movie.title,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            )
            Text(text = movie.title, modifier = Modifier.padding(8.dp))
        }
    }
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(
    searchTMDB: (String) -> Unit,
    posterIMGList: Any?,
) {
    val searchQueryState = rememberSaveable(stateSaver = TextFieldValue.Saver)  {
        mutableStateOf(TextFieldValue(String()))
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = searchQueryState.value,
        onValueChange = { value ->
            searchQueryState.value = value
        },
//        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(fontSize = 18.sp),
        leadingIcon = {
            if (searchQueryState.value != TextFieldValue(String())) {
                IconButton(
                    onClick = {
                        searchQueryState.value =
                            TextFieldValue(String()) // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove search argument",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                searchTMDB(searchQueryState.value.text)
                Log.d("overview: searchQueryState.value.text", searchQueryState.value.text)
//                Log.d("overvw test","hereee ${posterIMGList.toString()}")
                keyboardController?.hide()
            }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search for  movies in TMDB based on search argument provided",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp),
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search_movie_hint)
            )
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
    )
}


//@Composable
//fun Movie(
//    posterIMGlist: String?
//){
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3)
//    ) {
//        items(items = posterIMGlist, itemContent = { item ->
//            MovieCard(item)
//            Log.d("Movie heree", item)
//        }
//        )
//    }
//}

//@Composable
//fun MovieCard(
//    item: String
//) {
//    Card (
//        modifier = Modifier,
//        onClick = {}
//    ){
//        Image(
//            painter = rememberAsyncImagePainter(model = item),
//            contentDescription = "cat image here",
//            modifier = Modifier
//                .height(300.dp)
//                .width(300.dp)
//                .padding(15.dp)
//        )
//    }
//}


@Composable
fun FavButton(
    navController: NavHostController
) {
    Button(
        onClick = {
            navController.navigate(MovieScreens.FavoritesScreen.route)
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier.fillMaxWidth()


    ){
        Icon(
            imageVector = Icons.Default.ThumbUp,
            contentDescription = "thumbUp",
            tint =  Color.Green,
            modifier = Modifier
                .size(40.dp)
        )
    }
}


