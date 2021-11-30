package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieCategory(movieCategory: MovieCategory)

    //    get list of id by category
    @Query("SELECT * FROM MovieCategory WHERE category = :category")
    fun getMovieIdsByCategory(category: String): LiveData<List<MovieCategory>>

}