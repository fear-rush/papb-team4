package com.example.movieapp.view

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

import android.content.Intent
import android.util.Log
import android.view.Window
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.movieapp.api.MoviesRepository
import com.example.movieapp.database.MovieDatabase
import com.example.movieapp.model.Movie
import com.example.movieapp.R
import com.example.movieapp.database.MovieCategory
import com.example.movieapp.database.MovieCategoryDatabase
import kotlinx.android.synthetic.main.fragment_home.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewOfLayout: View
//    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_home, container, false)
        viewOfLayout.popular_movies.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        fun showMovieDetails(movie: Movie) {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
            intent.putExtra(MOVIE_POSTER, movie.posterPath)
            intent.putExtra(MOVIE_TITLE, movie.title)
            intent.putExtra(MOVIE_RATING, movie.rating)
            intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
            intent.putExtra(MOVIE_OVERVIEW, movie.overview)
            startActivity(intent)
        }
        viewOfLayout.top_rated_movies.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularMoviesAdapter = MoviesAdapter(listOf()) {movie -> showMovieDetails(movie)}
        topRatedMoviesAdapter = MoviesAdapter(listOf()) {movie -> showMovieDetails(movie)}
        viewOfLayout.popular_movies.adapter = popularMoviesAdapter
        viewOfLayout.top_rated_movies.adapter = topRatedMoviesAdapter


        MoviesRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
        MoviesRepository.getTopRatedMovies(
            onSuccess = ::onTopRatedMoviesFetched,
            onError = ::onError
        )
        Log.d(TAG, "fragment home berhasil")
        return viewOfLayout
    }

    val movieDb = context?.let {
        Room.databaseBuilder(
            it.applicationContext,
            MovieDatabase::class.java, "movies",
        ).allowMainThreadQueries().build()
    }

    val movieCategoryDb = context?.let {
        Room.databaseBuilder(
            it.applicationContext,
            MovieCategoryDatabase::class.java, "movie_category",
        ).allowMainThreadQueries().build()
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.updateMovies(movies)
//        // loop into each movie and make list of movie category, input id and set cateory to popular
//        for (movie in movies) {
//            val movieCategory = MovieCategory(movie.id, "popular")
//            movieCategoryDb?.movieCategoryDao()?.insertMovieCategory(movieCategory)
//        }
//        // put popular movies to database
//        movieDb?.movieDao()?.insertMovies(movies)
        Log.d("Repository", "Movies Fetched")
    }
    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.updateMovies(movies)
//        for (movie in movies) {
//            val movieCategory = MovieCategory(movie.id, "top_rated")
//            movieCategoryDb?.movieCategoryDao()?.insertMovieCategory(movieCategory)
//        }
//        movieDb?.movieDao()?.insertMovies(movies)
        Log.d("Repository", "Movies Fetched")
    }


    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT)
            .show()
    }

    private fun checkPopularMovieDatabase() {
        val movieDb = context?.let {
            Room.databaseBuilder(
                it.applicationContext,
                MovieDatabase::class.java,
                "movies")
                .allowMainThreadQueries()
                .build()
        }
        val movieCategoryDb = context?.let {
            Room.databaseBuilder(
                it.applicationContext,
                MovieCategoryDatabase::class.java,
                "movie_category")
                .allowMainThreadQueries()
                .build()
        }
        val movieDao = movieDb?.movieDao()
        val movieCategoryDao = movieCategoryDb?.movieCategoryDao()
        if (movieDao != null) {
            val popularMoviesIds = movieCategoryDao?.getMovieIdsByCategory("popular")
            // check if live data is empty
            if (popularMoviesIds?.value != null) {
                // loop into each movie id and get movie from database
                val popularMovieList = mutableListOf<Movie>()
                popularMoviesIds.value!!.forEach {
                    val movie = movieDao.getMovieById(it.movieId)
                    popularMovieList.add(movie.value!!)
                }
//                val popularMovies = movieDao.getMoviesByIds(popularMoviesIds.value!!)
                popularMoviesAdapter.updateMovies(popularMovieList)
            } else {
                onError()
            }
        }
    }

    private fun checkTopRatedMovieDatabase () {
        val movieDb = context?.let {
            Room.databaseBuilder(
                it.applicationContext,
                MovieDatabase::class.java,
                "movies"
            )
                .allowMainThreadQueries()
                .build()
        }
        val movieCategoryDb = context?.let {
            Room.databaseBuilder(
                it.applicationContext,
                MovieCategoryDatabase::class.java,
                "movie_category"
            )
                .allowMainThreadQueries()
                .build()
        }
        val movieDao = movieDb?.movieDao()
        val movieCategoryDao = movieCategoryDb?.movieCategoryDao()
        if (movieDao != null) {
            val topRatedMoviesIds = movieCategoryDao?.getMovieIdsByCategory("top_rated")
            // check if live data is empty
            if (topRatedMoviesIds?.value != null) {
                // loop into each movie id and get movie from database
                val topRatedMovieList = mutableListOf<Movie>()
                topRatedMoviesIds.value!!.forEach {
                    val movie = movieDao.getMovieById(it.movieId)
                    topRatedMovieList.add(movie.value!!)
                }
                //                val topRatedMovies = movieDao.getMoviesByIds(topRatedMoviesIds.value!!)
                topRatedMoviesAdapter.updateMovies(topRatedMovieList)
            } else {
                onError()
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) {
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }

        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window: Window = activity.window
            val winParams: WindowManager.LayoutParams = window.getAttributes()
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            window.setAttributes(winParams)
        }
    }
}