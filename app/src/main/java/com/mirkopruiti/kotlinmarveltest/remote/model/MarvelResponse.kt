package com.mirkopruiti.kotlinmarveltest.remote.model

data class MarvelResponse<T>(
    var code: String,
    var status: String,
    var copyright: String,
    var attributionText: String,
    var attributionHTML: String,
    var etag: String,
    var data: MarvelData<T>)