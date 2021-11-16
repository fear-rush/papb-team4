package com.example.movieapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//Movie entity for room database

@Entity
data class MovieEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name="title") var title: String,
    @ColumnInfo(name="poster_path") var poster_path: String,
    @ColumnInfo(name="overview") var overview: String,
    @ColumnInfo(name="release_date") var release_date: String,
    @ColumnInfo(name="vote_average") var vote_average: Double
)