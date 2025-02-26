package com.example.flixster

import com.google.gson.annotations.SerializedName

import kotlin.jvm.JvmField;

class TvShow {
    @JvmField
    @SerializedName("name") // "name" is used instead of "title" for TV shows
    var title: String? = null

    @JvmField
    @SerializedName("poster_path")
    var posterPath: String? = null

    @JvmField
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
}
