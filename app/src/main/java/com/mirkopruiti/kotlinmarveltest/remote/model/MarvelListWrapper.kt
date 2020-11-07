package com.mirkopruiti.kotlinmarveltest.remote.model

import java.io.Serializable
import java.util.*

data class MarvelListWrapper(
    var available: Int,
    var collectionURI: String,
    var items: ArrayList<MarvelListItem>?,
    var returned: Int)
    : Serializable