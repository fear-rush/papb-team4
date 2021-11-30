package com.example.movieapp

// DAO for Movie
import androidx.lifecycle.LiveData
import androidx.room.*


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

    //    get list of movie by list of id
    @Query("SELECT * FROM Movie WHERE id IN (:movieIds)")
    fun getMoviesByIds(movieIds: IntArray): LiveData<List<Movie>>

    //    get movie by id
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    fun getMovieById(movieId: Long): LiveData<Movie>

    @Query("SELECT * FROM Movie WHERE title LIKE :title")
    fun findByTitle(title: String): LiveData<List<Movie>>
}