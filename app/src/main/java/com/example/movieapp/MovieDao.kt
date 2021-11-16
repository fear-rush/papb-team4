package com.example.movieapp

// DAO for Movie
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.Movie


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    fun insertAll(movies: List<Movie>){
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
}