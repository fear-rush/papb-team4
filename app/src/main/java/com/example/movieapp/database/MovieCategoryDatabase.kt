package com.example.movieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieCategory::class], version = 1, exportSchema = false)
abstract class MovieCategoryDatabase : RoomDatabase() {
    abstract fun movieCategoryDao(): MovieCategoryDao
}
