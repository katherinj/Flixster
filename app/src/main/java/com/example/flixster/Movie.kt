package com.example.flixster

import com.google.gson.annotations.SerializedName

class Movie {
    @JvmField
    @SerializedName("title")
    var title: String? = null

    @JvmField
    @SerializedName("overview")
    var description: String? = null

    @JvmField
    @SerializedName("poster_path")
    var posterPath: String? = null

    @JvmField
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
}