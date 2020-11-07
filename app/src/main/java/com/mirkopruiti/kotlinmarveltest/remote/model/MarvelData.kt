package com.mirkopruiti.kotlinmarveltest.remote.model

import java.util.ArrayList

data class MarvelData<T>(
    var offset: Int,
    var limit: Int,
    var total: Int,
    var count: Int,
    var results: ArrayList<T>)