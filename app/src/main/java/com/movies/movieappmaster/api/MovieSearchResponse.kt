package com.movies.movieappmaster.api

import com.google.gson.annotations.SerializedName
import com.movies.movieappmaster.data.Movie

/**
 * Data class to hold repo responses from searchRepo API calls.
 */
data class MovieSearchResponse(
    @SerializedName("Search") val items: List<Movie> = emptyList(),
    val nextPage: Int? = null
)
