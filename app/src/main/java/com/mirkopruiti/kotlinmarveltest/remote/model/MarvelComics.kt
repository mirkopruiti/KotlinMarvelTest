package com.mirkopruiti.kotlinmarveltest.remote.model

import java.io.Serializable
import java.util.ArrayList

data class MarvelComics(
    var id: Int,
    var title: String,
    var thumbnail: MarvelThumbnail?,
    var prices : ArrayList<MarvelPrices>?)
    : Serializable
