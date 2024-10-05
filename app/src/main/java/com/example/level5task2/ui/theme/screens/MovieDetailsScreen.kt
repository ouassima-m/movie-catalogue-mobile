package com.example.level5task2.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.level5task2.R
import com.example.level5task2.viewmodel.ViewModel

@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
    viewModel: ViewModel
){
    Scaffold (
        content = { innerPadding ->
            DetailsScreenContent(
                modifier= Modifier.padding(innerPadding),
            )
        }
    )
}

@Composable
fun DetailsScreenContent(modifier: Modifier) {
    Column (){
        DetailsAddButton()
    }
}


@Composable
fun DetailsAddButton() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text = stringResource(R.string.add_and_remove_fav))


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
