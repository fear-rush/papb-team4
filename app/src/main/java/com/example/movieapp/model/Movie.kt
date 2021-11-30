package com.example.movieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey@SerializedName("id") val id: Long,
    @ColumnInfo@SerializedName("title") val title: String,
    @ColumnInfo@SerializedName("overview") val overview: String,
    @ColumnInfo@SerializedName("poster_path") val posterPath: String,
    @ColumnInfo@SerializedName("backdrop_path") val backdropPath: String,
    @ColumnInfo@SerializedName("vote_average") val rating: Float,
    @ColumnInfo@SerializedName("release_date") val releaseDate: String
)