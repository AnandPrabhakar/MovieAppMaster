package com.movies.movieappmaster.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movies.movieappmaster.data.MovieRepository
import com.movies.movieappmaster.viewmodel.SearchMoviesViewModel

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(SearchMoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchMoviesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}