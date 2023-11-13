package com.sithuheinn.mm.codemanagement.domain.interactor

import com.sithuheinn.mm.codemanagement.data.local.FavouriteMovieEntity
import com.sithuheinn.mm.codemanagement.data.local.MovieDao
import com.sithuheinn.mm.codemanagement.domain.Movie
import javax.inject.Inject


class SaveFavouriteMovie @Inject constructor(
    private val dao: MovieDao

) {
    suspend operator fun invoke(entity: FavouriteMovieEntity) {
        dao.saveFavouriteMovie(entity)
    }
}