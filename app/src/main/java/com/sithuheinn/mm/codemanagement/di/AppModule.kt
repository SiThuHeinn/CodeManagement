package com.sithuheinn.mm.codemanagement.di

import android.app.Application
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.sithuheinn.mm.codemanagement.data.api.MovieApi
import com.sithuheinn.mm.codemanagement.data.local.MovieDao
import com.sithuheinn.mm.codemanagement.data.local.MovieDatabase
import com.sithuheinn.mm.codemanagement.data.local.PopularMovieEntity
import com.sithuheinn.mm.codemanagement.data.local.UpcomingMovieEntity
import com.sithuheinn.mm.codemanagement.data.remote.PopularMovieRemoteMediator
import com.sithuheinn.mm.codemanagement.data.remote.UpcomingMovieRemoteMediator
import com.sithuheinn.mm.codemanagement.network.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Collections
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun context(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie.db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDatabase): MovieDao {
        return db.movieDao
    }

    @Provides
    @Singleton
    fun providePopularMoviePager(
        movieDb: MovieDatabase, movieApi: MovieApi
    ): Pager<Int, PopularMovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = PopularMovieRemoteMediator(movieDb, api = movieApi),
            pagingSourceFactory = {
                movieDb.movieDao.popularMoviePagingSource()
            }
        )
    }

    @Provides
    @Singleton
    fun provideUpcomingMoviePager(
        movieDb: MovieDatabase, movieApi: MovieApi
    ): Pager<Int, UpcomingMovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = UpcomingMovieRemoteMediator(movieDb, api = movieApi),
            pagingSourceFactory = {
                movieDb.movieDao.upcomingMoviePagingSource()
            }
        )
    }


    @Singleton
    @Provides
    fun provideOkHttp(
        context: Context
    ): OkHttpClient {
        val client = OkHttpClient.Builder()

        with(client) {

//            if (BuildConfig.DEBUG) {
//                val loggingInterceptor = HttpLoggingInterceptor()
//                loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
//                addNetworkInterceptor(loggingInterceptor)
//
//            }
            addInterceptor(HeaderInterceptor())
            protocols(Collections.singletonList(Protocol.HTTP_1_1))
            connectTimeout(10, TimeUnit.MINUTES)
            writeTimeout(10, TimeUnit.MINUTES)
            readTimeout(10, TimeUnit.MINUTES)
        }
        return client.build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val baseUrl = "https://api.themoviedb.org/3/" // base api url shouldn't be save here. but its just an testing app.
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

}