package com.example.movieapp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {

    private val apiPopular: ApiPopular
    private val apiTopRated: ApiTopRated

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiPopular = retrofit.create(ApiPopular::class.java)
        apiTopRated = retrofit.create(ApiTopRated::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        apiPopular.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
//                            Log.d("Repository", "Movies: ${responseBody.movies}")
                            onSuccess.invoke(responseBody.movies)
                        } else {
//                            Log.d("Repository", "Failed to get response")
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    Log.e("Repository", "onFailure", t)
                    onError.invoke()
                }
            })
    }

    fun getTopRatedMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        apiTopRated.getTopRatedMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
//                            Log.d("Repository", "Movies: ${responseBody.movies}")
                            onSuccess.invoke(responseBody.movies)
                        } else {
//                            Log.d("Repository", "Failed to get response")
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    Log.e("Repository", "onFailure", t)
                    onError.invoke()
                }
            })
    }
}