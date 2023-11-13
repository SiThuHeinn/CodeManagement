package com.sithuheinn.mm.codemanagement.data.mapper

import com.sithuheinn.mm.codemanagement.data.dto.MovieDto
import com.sithuheinn.mm.codemanagement.data.local.FavouriteMovieEntity
import com.sithuheinn.mm.codemanagement.data.local.PopularMovieEntity
import com.sithuheinn.mm.codemanagement.data.local.UpcomingMovieEntity
import com.sithuheinn.mm.codemanagement.domain.Movie


fun MovieDto.toPopularMovieEntity(): PopularMovieEntity {
    return PopularMovieEntity(
        id = this.id,
        adult = this.adult,
        backdropPath = getCompletePostPath(this.backdrop_path),
        originalLanguage = this.original_language,
        originalTitle = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = getCompletePostPath(this.poster_path),
        releaseDate = this.release_date,
        title = this.title,
        voteAverage = this.vote_average,
        voteCount = this.vote_count

    )
}

fun MovieDto.toUpcomingMovieEntity(): PopularMovieEntity {
    return PopularMovieEntity(
        id = this.id,
        adult = this.adult,
        backdropPath = getCompletePostPath(this.backdrop_path),
        originalLanguage = this.original_language,
        originalTitle = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = getCompletePostPath(this.poster_path),
        releaseDate = this.release_date,
        title = this.title,
        voteAverage = this.vote_average,
        voteCount = this.vote_count

    )
}


fun UpcomingMovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

fun PopularMovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}


fun Movie.toFavouriteEntity(): FavouriteMovieEntity {
    return FavouriteMovieEntity(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

fun FavouriteMovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

private fun getCompletePostPath(tail: String): String = "https://image.tmdb.org/t/p/w500/$tail"