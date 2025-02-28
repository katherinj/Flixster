package com.example.flixster

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TvShow(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val title: String?,

    @SerializedName("overview")
    val description: String?,

    @SerializedName("first_air_date")
    val releaseDate: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("vote_average")
    val voteAverage: Double?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("original_language")
    val originalLanguage: String?,
    ) : Serializable
