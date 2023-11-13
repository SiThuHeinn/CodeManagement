package com.sithuheinn.mm.codemanagement.presentation.feature

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sithuheinn.mm.codemanagement.data.mapper.toMovie
import com.sithuheinn.mm.codemanagement.domain.Movie
import com.sithuheinn.mm.codemanagement.domain.interactor.GetFavouriteMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteMovieViewModel @Inject constructor(
    private val getFavouriteMovies: GetFavouriteMovies
): ViewModel() {

    private val _movies: MutableState<List<Movie>> = mutableStateOf(emptyList())
    val movies = _movies

    init {
        viewModelScope.launch {
            val data = getFavouriteMovies.invoke()
            _movies.value = data.map { it.toMovie() }
        }
    }

}