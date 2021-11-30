package com.example.movieapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieCategory(
    @PrimaryKey var movieId: Long,
    @ColumnInfo(name="category") var category: String
)