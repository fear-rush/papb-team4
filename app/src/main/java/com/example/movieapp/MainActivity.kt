package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        fun onError() {
            Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
        }

        // function to check if database is empty, if so, call onError
//        fun checkDatabase() {
//            val db = Room.databaseBuilder(applicationContext, MovieDatabase::class.java, "movies").build()
//            val movieDao = db.movieDao()
//            if (movieDao.getMovieCount() == 0) {
//                onError()
//            }
//        }

        val db = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java, "movies"
        ).allowMainThreadQueries().build()

        val movCatDb = Room.databaseBuilder(
            applicationContext,
            MovieCategoryDatabase::class.java, "movie_categories"
        ).allowMainThreadQueries().build()

        fun onPopularMoviesFetched(movies: List<Movie>) {
            Log.d("MainActivity", "Movies: $movies")
            // put movies id in popular category database
            for (movie in movies) {
                movCatDb.movieCategoryDao().insertMovieCategory(MovieCategory(movie.id, "popular"))
            }
            db.movieDao().insertAll(movies)
        }
        fun onTopRatedMoviesFetched(movies: List<Movie>) {
            Log.d("MainActivity", "Movies: $movies")
            // put movies id in top rated category database
            for (movie in movies) {
                movCatDb.movieCategoryDao().insertMovieCategory(MovieCategory(movie.id, "top_rated"))
            }
            db.movieDao().insertAll(movies)
        }

//        fun checkPopularMoviesOnDb(){
//            val movieDao = db.movieDao()
//            val movieCategoryDao = movCatDb.movieCategoryDao()
//            val popularMoviesIds = movieCategoryDao.getMovieIdsByCategory("popular")
//            if (popularMoviesIds?.value != null) {
//                // loop into each movie id and get movie from database
//                val popularMovieList = mutableListOf<Movie>()
//                popularMoviesIds.value!!.forEach {
//                    val movie = movieDao.getMovieById(it.movieId)
//                    popularMovieList.add(movie.value!!)
//                }
////                val popularMovies = movieDao.getMoviesByIds(popularMoviesIds.value!!)
////                popularMoviesAdapter.updateMovies(popularMovieList)
//            } else {
//                onError()
//            }
//        }

        MoviesRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
        MoviesRepository.getTopRatedMovies(
            onSuccess = ::onTopRatedMoviesFetched,
            onError = ::onError
        )

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)

        bottomNavigationView.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.aboutFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}