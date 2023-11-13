package com.sithuheinn.mm.codemanagement.presentation.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sithuheinn.mm.codemanagement.domain.Movie
import com.sithuheinn.mm.codemanagement.presentation.design.MovieItem


@Composable
fun FavouriteMovieScreen(
    movies: List<Movie>,
    onItemClick: (Int) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = movies) {
                MovieItem(movie = it, modifier = Modifier.fillMaxWidth(), onItemClick = { id ->
                    onItemClick.invoke(id)
                })
            }
        }
    }


}