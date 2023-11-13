package com.sithuheinn.mm.codemanagement.presentation.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.sithuheinn.mm.codemanagement.domain.interactor.GetMovieDetail
import com.sithuheinn.mm.codemanagement.presentation.feature.FavouriteMovieScreen
import com.sithuheinn.mm.codemanagement.presentation.feature.FavouriteMovieViewModel
import com.sithuheinn.mm.codemanagement.presentation.feature.MovieDetailScreen
import com.sithuheinn.mm.codemanagement.presentation.feature.MovieDetailViewModel
import com.sithuheinn.mm.codemanagement.presentation.feature.PopularMovieScreen
import com.sithuheinn.mm.codemanagement.presentation.feature.PopularMovieViewModel
import com.sithuheinn.mm.codemanagement.presentation.feature.UpcomingMovieScreen
import com.sithuheinn.mm.codemanagement.presentation.feature.UpcomingMovieViewModel


@Composable
fun SetupBottomNavGraph(
    navController: NavHostController
) {



    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Popular.route,
    ) {

        composable(route = BottomBarScreen.Popular.route) {
            val vm = hiltViewModel<PopularMovieViewModel>()
            val movies = vm.pagingFlow.collectAsLazyPagingItems()
            PopularMovieScreen(movies) {
                navController.navigate("movie_detail/$it/${GetMovieDetail.MovieType.Popular.str}")
            }
        }

        composable(route = BottomBarScreen.Upcoming.route) {
            val vm = hiltViewModel<UpcomingMovieViewModel>()
            val movies = vm.pagingFlow.collectAsLazyPagingItems()
            UpcomingMovieScreen(movies) {
                navController.navigate("movie_detail/$it/${GetMovieDetail.MovieType.Upcoming.str}")
            }
        }

        composable(route = BottomBarScreen.Favourite.route) {
            val vm = hiltViewModel<FavouriteMovieViewModel>()
            val movies = vm.movies.value
            FavouriteMovieScreen(movies) {
                navController.navigate("movie_detail/$it/${GetMovieDetail.MovieType.Favourite.str}")
            }
        }
        
        composable(
            route = "movie_detail/{id}/{type}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType},
                navArgument("type") { type = NavType.StringType}
            )
        ) {
            val id = it.arguments?.getInt("id")
            val type = it.arguments?.getString("type")
            val vm = hiltViewModel<MovieDetailViewModel>()
            vm.load(id, type)
            val data = vm.movieDetail.value
            MovieDetailScreen(data = data, favorite = vm::save)

        }
    }
}


@Composable
fun BottomBar(navController: NavHostController) {

    val screens = listOf(
        BottomBarScreen.Popular,
        BottomBarScreen.Upcoming,
        BottomBarScreen.Favourite,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    NavigationBar {
        screens.forEach { screen -> 
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }
    }

}


@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    NavigationBarItem(
        icon = { Icon(imageVector = screen.icon, contentDescription = "Navigation Icon") },
        label = { Text(text = screen.title)},
        selected = currentDestination?.hierarchy?.any{ it.route == screen.route } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )

}