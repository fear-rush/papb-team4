package com.example.movieapp.database

// DAO for Movie
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.model.Movie


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    fun insertMovies(movies: List<Movie>){
        Log.d("MovieDao", "Inserting all movies")
        movies.forEach {
            insert(it)
        }
    }

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM Movie")
    fun deleteAllMovies()

    @Query("SELECT * FROM Movie ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT COUNT(title) FROM Movie")
    fun getMovieCount(): Int

//    get list of movie by list of id
    @Query("SELECT * FROM Movie WHERE id IN (:movieIds)")
    fun getMoviesByIds(movieIds: IntArray): LiveData<List<Movie>>

//    get movie by id
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    fun getMovieById(movieId: Long): LiveData<Movie>

    @Query("SELECT * FROM Movie WHERE title LIKE :title")
    fun findByTitle(title: String): LiveData<List<Movie>>
}