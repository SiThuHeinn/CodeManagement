package com.sithuheinn.mm.codemanagement.domain.interactor

import com.sithuheinn.mm.codemanagement.data.local.MovieDao
import com.sithuheinn.mm.codemanagement.data.mapper.toMovie
import com.sithuheinn.mm.codemanagement.domain.Movie
import javax.inject.Inject

/**
 * It's better to use with repository pattern, but for sake of test during, i mma leave it here with only user case class.
 * Get movie detail
 *
 * @property dao
 * @constructor Create empty Get movie detail
 */
class GetMovieDetail @Inject constructor(
    private val dao: MovieDao
) {

    suspend operator fun invoke(id: Int, type: MovieType): Movie {

        return when (type) {
            MovieType.Popular -> {
                dao.getPopularMovieDetail(id).toMovie()
            }
            MovieType.Upcoming -> {
                dao.getUpcomingMovieDetail(id).toMovie()
            }
            else -> {
                dao.getFavouriteMovieDetail(id).toMovie()
            }
        }
    }


    enum class MovieType(val str: String) {
        Popular("popular"),
        Upcoming("upcoming"),
        Favourite("favourite");

        companion object {
            fun convertFromCode(value: String): MovieType {
                return when(value) {
                    "popular" -> Popular
                    "upcoming" -> Upcoming
                    "favourite" -> Favourite
                    else -> throw Exception("can't process.")
                }
            }
        }
    }
}