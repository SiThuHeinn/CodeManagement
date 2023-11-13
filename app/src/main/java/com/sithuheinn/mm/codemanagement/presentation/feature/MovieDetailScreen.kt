package com.sithuheinn.mm.codemanagement.presentation.feature

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sithuheinn.mm.codemanagement.domain.Movie


@Composable
fun MovieDetailScreen(
    data: UiState<Movie>,
    favorite: (Movie) -> Unit
) {

    val content = LocalContext.current


    val liked = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {


        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = {
                liked.value = !liked.value
                favorite.invoke(data.data!!)
            }
        ) {
            Icon(
                imageVector = if (liked.value.not()) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = "favourite")
        }


        Column(modifier = Modifier.padding(top = 40.dp)) {
            AsyncImage(
                model = data.data?.backdropPath,
                contentDescription = data.data?.title.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)

            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.data?.title.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Overview", style = MaterialTheme.typography.titleMedium)

            Text(text = data.data?.overview.orEmpty())
        }

    }
}