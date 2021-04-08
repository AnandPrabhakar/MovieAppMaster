package com.movies.movieappmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.movies.movieappmaster.data.Movie
import com.movies.movieappmaster.data.MovieRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesViewModel (private val repository: MovieRepository) : ViewModel()
{

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Movie>>? = null
    fun searchRepo(queryString: String): Flow<PagingData<Movie>>
    {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Movie>> = repository.getSearchResultStream(queryString).cachedIn(viewModelScope)

        currentSearchResult = newResult;
        return newResult;

    }

}

