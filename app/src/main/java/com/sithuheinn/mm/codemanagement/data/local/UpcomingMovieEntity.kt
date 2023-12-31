package com.sithuheinn.mm.codemanagement.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UpcomingMovieEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
)
