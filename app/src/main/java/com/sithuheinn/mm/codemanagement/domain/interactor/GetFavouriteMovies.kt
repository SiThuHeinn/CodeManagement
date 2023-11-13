package com.sithuheinn.mm.codemanagement.domain.interactor

import com.sithuheinn.mm.codemanagement.data.local.FavouriteMovieEntity
import com.sithuheinn.mm.codemanagement.data.local.MovieDao
import javax.inject.Inject

class GetFavouriteMovies @Inject constructor(
    private val dao: MovieDao

) {

    suspend operator fun invoke(): List<FavouriteMovieEntity> {
        return dao.getFavouriteMovies()
    }
}