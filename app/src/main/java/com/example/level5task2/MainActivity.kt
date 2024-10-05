package com.example.level5task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level5task2.ui.theme.Level5Task2Theme
import com.example.level5task2.ui.theme.screens.FavoritesScreen
import com.example.level5task2.ui.theme.screens.MovieDetailsScreen
import com.example.level5task2.ui.theme.screens.MovieScreens
import com.example.level5task2.ui.theme.screens.OverviewScreen
import com.example.level5task2.viewmodel.ViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Level5Task2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize().statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, modifier = Modifier)
                }
            }
        }
    }
}

@Composable
private fun NavHost(
    navController: NavHostController, modifier: Modifier
) {
    val viewModel: ViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = MovieScreens.OverviewScreen.route,
        modifier = modifier
    ) {
        composable ( route = MovieScreens.OverviewScreen.route) {
            OverviewScreen(navController, viewModel)
        }
        composable ( route = MovieScreens.FavoritesScreen.route) {
            FavoritesScreen(navController, viewModel)
        }
        composable ( route = MovieScreens.MovieDetailsScreen.route) {
            MovieDetailsScreen(navController, viewModel)
        }
    }
}