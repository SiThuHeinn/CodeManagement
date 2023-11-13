package com.sithuheinn.mm.codemanagement.data.dto

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class MovieDto(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)


@JsonClass(generateAdapter = true)
data class MovieResponse(
    val results: List<MovieDto>
)


//https://image.tmdb.org/t/p/w500
