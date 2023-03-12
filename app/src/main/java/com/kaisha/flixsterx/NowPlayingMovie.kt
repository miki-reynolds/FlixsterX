package com.kaisha.flixsterx

import com.google.gson.annotations.SerializedName


class NowPlayingMovie  {
    @JvmField
    @SerializedName("poster_path")
    var poster: String ? = null

    @JvmField
    @SerializedName("title")
    var title: String ? = null

    @JvmField
    @SerializedName("overview")
    var overview: String ? = null
}