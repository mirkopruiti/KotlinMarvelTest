package com.mirkopruiti.kotlinmarveltest.remote.model

import java.io.Serializable

data class MarvelUrl(
    var type: String,
    var url: String)
    : Serializable