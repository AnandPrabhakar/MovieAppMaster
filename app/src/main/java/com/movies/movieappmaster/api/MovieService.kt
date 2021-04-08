package com.movies.movieappmaster.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService
{
    @GET("/?apikey=c1fc9830&")
    suspend fun searchRepos(
        @Query("s") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): MovieSearchResponse


    companion object
    {
        private const val BASE_URL = "http://www.omdbapi.com"

        fun create(): MovieService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService::class.java)
        }
    }
}