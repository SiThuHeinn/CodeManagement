package com.sithuheinn.mm.codemanagement.presentation.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.sithuheinn.mm.codemanagement.data.local.PopularMovieEntity
import com.sithuheinn.mm.codemanagement.data.mapper.toMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    pager: Pager<Int, PopularMovieEntity>
): ViewModel() {

    val pagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toMovie() }
        }
        .cachedIn(viewModelScope)
}