package com.sithuheinn.mm.codemanagement.presentation.feature

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.sithuheinn.mm.codemanagement.domain.Movie
import com.sithuheinn.mm.codemanagement.presentation.design.MovieItem


@Composable
fun UpcomingMovieScreen(
    movies: LazyPagingItems<Movie>,
    onItemClick: (Int) -> Unit
) {


    val context = LocalContext.current

    LaunchedEffect(key1 = movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            Toast.makeText(context, "Error is: ${(movies.loadState.refresh as LoadState.Error).error.message})", Toast.LENGTH_SHORT).show()
            Log.d("upcomingmovie", "Error is: ${(movies.loadState.refresh as LoadState.Error).error.message})")
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        if (movies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(movies.itemCount) { index ->
                    val movie = movies[index]
                    if (movie != null) {
                        MovieItem(
                            movie = movie,
                            modifier =  Modifier.fillMaxSize()
                        ) {
                            onItemClick.invoke(it)
                        }
                    }
                }

                item {
                    if (movies.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }


}