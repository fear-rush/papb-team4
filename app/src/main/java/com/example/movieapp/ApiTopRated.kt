package com.example.movieapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTopRated {
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "f52be8110773a439a6bd9ddaaf4916b9",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}