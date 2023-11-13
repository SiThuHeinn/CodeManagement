package com.sithuheinn.mm.codemanagement.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface MovieDao {


    @Upsert
    suspend fun upsertPopularMovies(movies: List<PopularMovieEntity>)

    @Upsert
    suspend fun upsertUpcomingMovies(movies: List<PopularMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavouriteMovie(movie: FavouriteMovieEntity)

    @Query("SELECT * FROM favouritemovieentity")
    suspend fun getFavouriteMovies(): List<FavouriteMovieEntity>


    @Query("SELECT * FROM popularmovieentity")
    fun popularMoviePagingSource(): PagingSource<Int, PopularMovieEntity>

    @Query("SELECT * FROM upcomingmovieentity")
    fun upcomingMoviePagingSource(): PagingSource<Int, UpcomingMovieEntity>

    @Query("SELECT * FROM popularmovieentity where id = :id")
    suspend fun getPopularMovieDetail(id: Int): PopularMovieEntity

    @Query("SELECT * FROM upcomingmovieentity where id = :id")
    suspend fun getUpcomingMovieDetail(id: Int): UpcomingMovieEntity

    @Query("SELECT * FROM favouritemovieentity where id = :id")
    suspend fun getFavouriteMovieDetail(id: Int): FavouriteMovieEntity


    @Query("DELETE FROM popularmovieentity")
    suspend fun clearAllPopularMovies()

    @Query("DELETE FROM upcomingmovieentity")
    suspend fun clearAllUpcomingMovies()

}