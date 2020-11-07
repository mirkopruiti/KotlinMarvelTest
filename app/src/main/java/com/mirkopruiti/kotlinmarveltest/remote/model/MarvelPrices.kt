package com.mirkopruiti.kotlinmarveltest.remote.model

import java.io.Serializable

data class MarvelPrices(
    var type: String,
    var price: Float)
    : Serializable