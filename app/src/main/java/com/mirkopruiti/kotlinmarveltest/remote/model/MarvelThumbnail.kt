package com.mirkopruiti.kotlinmarveltest.remote.model

import java.io.Serializable

data class MarvelThumbnail(
    var path: String,
    var extension: String)
    : Serializable