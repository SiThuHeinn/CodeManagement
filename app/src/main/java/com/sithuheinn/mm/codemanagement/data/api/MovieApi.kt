package com.sithuheinn.mm.codemanagement.data.api

import com.sithuheinn.mm.codemanagement.data.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): MovieResponse

}
