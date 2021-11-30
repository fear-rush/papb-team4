package com.example.movieapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiPopular {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "f52be8110773a439a6bd9ddaaf4916b9",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}