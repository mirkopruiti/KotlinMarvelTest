package com.mirkopruiti.kotlinmarveltest.remote.model

import java.io.Serializable

data class MarvelListItem(
    var resourceURI: String,
    var name: String)
    : Serializable