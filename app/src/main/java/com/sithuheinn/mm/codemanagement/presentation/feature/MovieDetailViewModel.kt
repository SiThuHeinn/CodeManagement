package com.sithuheinn.mm.codemanagement.presentation.feature

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sithuheinn.mm.codemanagement.data.local.FavouriteMovieEntity
import com.sithuheinn.mm.codemanagement.data.mapper.toFavouriteEntity
import com.sithuheinn.mm.codemanagement.domain.Movie
import com.sithuheinn.mm.codemanagement.domain.interactor.GetMovieDetail
import com.sithuheinn.mm.codemanagement.domain.interactor.SaveFavouriteMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    private val saveFavouriteMovie: SaveFavouriteMovie
) : ViewModel() {

    private val _movieDetail: MutableState<UiState<Movie>> = mutableStateOf(UiState.Nothing())
    val movieDetail = _movieDetail



    fun load(id: Int?, type: String?) {
        if (id == null || type == null) return
        viewModelScope.launch {
            try {
                val result = getMovieDetail.invoke(id, GetMovieDetail.MovieType.convertFromCode(type))
                _movieDetail.value = UiState.Success(result)
            }catch (e: Exception) {
                _movieDetail.value = UiState.Error(e.message)
            }
        }

    }

    fun save(movie: Movie) {
        viewModelScope.launch {
            saveFavouriteMovie.invoke(movie.toFavouriteEntity())
        }
    }

}