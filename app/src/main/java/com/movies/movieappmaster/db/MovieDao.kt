package com.movies.movieappmaster.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.movieappmaster.data.Movie

@Dao
interface MovieDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Movie>)

    @Query(
        "SELECT * FROM Movies WHERE name LIKE :queryString OR year LIKE :queryString"
    )
     fun searchByMovieDetails(queryString: String): PagingSource<Int,Movie>

    @Query(
        "SELECT * FROM Movies "
    )
    fun searchByMovieDetails(): PagingSource<Int,Movie>
    @Query("DELETE FROM Movies")
    suspend fun clearRepos()
}