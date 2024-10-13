package com.example.level5task2.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import coil.compose.rememberAsyncImagePainter
import com.example.level5task2.R
import com.example.level5task2.data.api.util.Resource
import com.example.level5task2.data.model.MovieResult
import com.example.level5task2.data.model.Movies
import com.example.level5task2.viewmodel.ViewModel

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: ViewModel
){
    val favMovieResource: Resource<List<Movies>>? by viewModel.favMoviesResource.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getFavMovieFromFirestore()
    }

    Scaffold (
        content = { innerPadding ->
            Column (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ){
                Row (
                ){
                    FavSearchView {  }
                    OverviewButton(
                        navController = navController
                    )

                }
                FavScreenContent(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    favMovieResource = favMovieResource
                )
            }
        }
    )
}


@Composable
fun FavScreenContent(
    viewModel: ViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    favMovieResource: Resource<List<Movies>>? = null
) {
        FavHeader()

        val favPosterIMGList = when (favMovieResource) {
            is Resource.Success -> stringResource(id = R.string.success)
            is Resource.Error -> favMovieResource?.message
            is Resource.Loading -> stringResource(R.string.loading_text)
//            is Resource.Empty -> stringResource(id = R.string.maybe_none_empty)
            else -> stringResource(R.string.something_wrong_state)
        }

        Column(
            modifier.padding(1.dp)
        )
        {
            if (favMovieResource is Resource.Empty || (favMovieResource?.data?.isNullOrEmpty() == true)){
                Text(
                    text = stringResource(R.string.maybe_none_empty),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            else if (favPosterIMGList.equals(stringResource(id = R.string.success))){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    favMovieResource?.data?.let { movies ->
                        items(movies) { movie ->
                            FavMovieCard(
                                navController = navController,
                                movie = movie,
                                viewModel = viewModel

                            )
                        }
                    }
                }
            }

    }
}


@Composable
fun FavMovieCard(
    navController: NavHostController,
    movie: Movies,
    viewModel: ViewModel
) {
    Card(
//        modifier = Modifier.padding(8.dp)
        onClick = {
            navController.navigate(MovieScreens.MovieDetailsScreen.route + "/${movie.id}")
            Log.d("MovieCard(fav): movie.id", movie.id.toString())
        }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model =
            //todo
            "https://image.tmdb.org/t/p/w500${movie.posterIMG}"
            ),
            contentDescription = movie.title,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun FavHeader() {
    Text(
        text = stringResource(R.string.favHeader),
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(20.dp)
    )

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FavSearchView(
    searchTMDB: (String) -> Unit
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


@Composable
fun OverviewButton(navController: NavHostController) {
    Button(
        onClick = {
            navController.popBackStack()
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


