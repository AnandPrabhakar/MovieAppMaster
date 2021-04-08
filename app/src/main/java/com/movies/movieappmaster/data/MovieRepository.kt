package com.movies.movieappmaster.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.movies.movieappmaster.api.MovieService
import com.movies.movieappmaster.db.MovieDatabase
import kotlinx.coroutines.flow.Flow

class MovieRepository( private val service: MovieService,
                       private val database: MovieDatabase)
{
    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */

    fun getSearchResultStream(query: String): Flow<PagingData<Movie>>
    {
        Log.d("GithubRepository", "New query: $query")

        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.reposDao().searchByMovieDetails(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
            remoteMediator = MovieRemoteMediator   (
                query,
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 15
    }
}