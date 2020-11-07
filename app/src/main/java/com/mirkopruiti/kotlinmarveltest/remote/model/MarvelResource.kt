package com.mirkopruiti.kotlinmarveltest.remote.model

import java.io.Serializable

data class MarvelResource(
    var id: Int,
    var title: String,
    var thumbnail: MarvelThumbnail?,
    var type: String)
    : Serializable
