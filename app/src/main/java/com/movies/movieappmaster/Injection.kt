package com.movies.movieappmaster

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.movies.movieappmaster.api.MovieService
import com.movies.movieappmaster.data.MovieRepository
import com.movies.movieappmaster.db.MovieDatabase
import com.movies.movieappmaster.views.ViewModelFactory

object Injection {

    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(context: Context): MovieRepository {
        return MovieRepository(MovieService.create(), MovieDatabase.getInstance(context))
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }

}