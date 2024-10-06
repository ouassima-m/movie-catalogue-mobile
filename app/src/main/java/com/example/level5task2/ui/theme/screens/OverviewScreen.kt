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
import androidx.compose.foundation.layout.width
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
import com.example.level5task2.data.model.Movies
import com.example.level5task2.viewmodel.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import coil.compose.rememberAsyncImagePainter





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    navController: NavHostController,
    viewModel: ViewModel
){
    val movieResource : Resource<List<Movies>>? by viewModel.movieResource.observeAsState()

//    val posterIMGList = when (movieResource) {
//        is Resource.Success -> movieResource?.data?.map { it.posterIMG } ?: emptyList()
//        is Resource.Error -> movieResource?.message
//        is Resource.Loading -> stringResource(R.string.loading_text)
//        is Resource.Empty -> stringResource(id = R.string.empty_posterIMG)
//        else -> stringResource(R.string.something_wrong_state)
//    }

    val posterIMGList: List<String> = when (movieResource)  {
        is Resource.Success ->
            movieResource?.data?.map { it.posterIMG } ?: emptyList()

        is Resource.Error ->
            emptyList()

        is Resource.Loading ->
            emptyList()

        is Resource.Empty ->
            emptyList()

        else ->
            emptyList()
    }

    Log.d("Movie here", movieResource.toString())


    Scaffold (
        content = { innerPadding ->
            Column (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
//                viewModel.getMovies()
            ){
                Row (
                ){
                    SearchView {  }
                    FavButton(navController = navController)

                }

                Movie(
                    posterIMGlist = posterIMGList
                )
            }

        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(
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
                // TODO your logic here

                //based on @ExperimentalComposeUiApi - if this doesn't work in a newer version remove it
                //no alternative in compose for hiding keyboard at time of writing
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

@Composable
fun Movie(
    posterIMGlist: List<String>
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(items = posterIMGlist, itemContent = { item ->
            MovieCard(item)
            Log.d("Movie heree", item)
        }
        )
    }
}

@Composable
fun MovieCard(
    item: String
) {
    Card (
        modifier = Modifier,
        onClick = {}
    ){
        Image(
            painter = rememberAsyncImagePainter(model = item),
            contentDescription = "cat image here",
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
                .padding(15.dp)
        )
    }
}
