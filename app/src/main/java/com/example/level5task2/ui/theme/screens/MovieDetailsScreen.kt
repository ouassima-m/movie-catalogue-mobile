package com.example.level5task2.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.level5task2.R
import com.example.level5task2.data.model.Movies
import com.example.level5task2.viewmodel.ViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
    viewModel: ViewModel,
    movieId: Int?
){
    val movieDetails: Movies? = viewModel.getMovieById(movieId)

    Scaffold (
        content = { innerPadding ->
            DetailsScreenContent(
                modifier= Modifier.padding(innerPadding),
                movieDetails = movieDetails
            )
        },
        bottomBar = {
            DetailsAddButton()
        }
    )
}


@Composable
fun DetailsScreenContent(modifier: Modifier, movieDetails: Movies? = null) {

        if (movieDetails != null) {
            Column(
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "https://image.tmdb.org/t/p/w500${movieDetails.headerIMG}"
                    ),
                    contentDescription = movieDetails.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                )
                Row (
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = "https://image.tmdb.org/t/p/w500${movieDetails.posterIMG}"
                        ),
                        contentDescription = movieDetails.title,
                        modifier = Modifier
                            .height(230.dp).clip(RoundedCornerShape(10.dp))
                    )
                    Box (
                        modifier = Modifier.padding(16.dp).fillMaxWidth().height(230.dp)
                    ) {
                        Text(
                            text = movieDetails.title,
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(4.dp).align(Alignment.Center)
                        )
                        Row (
                            modifier = Modifier.padding(4.dp).align(Alignment.BottomStart)

                        ){
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "star",
                                modifier = Modifier
                            )
                            Text(
                                text = movieDetails.rating.toString(),
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(R.string.overview),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(12.dp)
                )

                LazyColumn (
                    modifier = Modifier.padding(bottom = 94.dp)
                ) {
                    item{
                        Text(
                            text = movieDetails.overview,
                            style = TextStyle(fontSize = 18.sp),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        } else {
            Text(text = "Movie details not available.", modifier = Modifier.padding(16.dp))
        }
    }



@Composable
fun DetailsAddButton() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.add_and_remove_fav),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(6.dp)
        )


        Button(
            onClick = {
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
}
