package com.sithuheinn.mm.codemanagement.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [UpcomingMovieEntity::class, PopularMovieEntity::class, FavouriteMovieEntity::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {


    abstract val movieDao: MovieDao
}