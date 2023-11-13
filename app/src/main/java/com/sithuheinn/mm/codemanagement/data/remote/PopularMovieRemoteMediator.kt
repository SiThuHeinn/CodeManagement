
package com.sithuheinn.mm.codemanagement.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sithuheinn.mm.codemanagement.data.api.MovieApi
import com.sithuheinn.mm.codemanagement.data.local.MovieDatabase
import com.sithuheinn.mm.codemanagement.data.local.PopularMovieEntity
import com.sithuheinn.mm.codemanagement.data.mapper.toPopularMovieEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PopularMovieRemoteMediator(
    private val movieDb: MovieDatabase,
    private val api: MovieApi
) : RemoteMediator<Int, PopularMovieEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularMovieEntity>
    ): MediatorResult {

        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val movies = api.getPopularMovies(
                page = loadKey
            )

            movieDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDb.movieDao.clearAllPopularMovies()
                }

                val entities = movies.results.map { it.toPopularMovieEntity() }
                movieDb.movieDao.upsertPopularMovies(entities)
            }

            MediatorResult.Success(
                endOfPaginationReached = movies.results.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}