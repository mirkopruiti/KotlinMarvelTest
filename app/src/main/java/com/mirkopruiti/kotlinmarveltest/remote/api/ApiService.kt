package com.mirkopruiti.kotlinmarveltest.remote.api

import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelCharacter
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelComics
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/public/characters")
    fun getCharactersList(@Query("offset") offset: Int, @Query("limit") limit: Int)
            : Observable<MarvelResponse<MarvelCharacter>>

    @GET("/v1/public/characters")
    fun getCharactersList(@Query("offset") offset: Int, @Query("limit") limit: Int,
                          @Query("nameStartsWith") name: String)
            : Observable<MarvelResponse<MarvelCharacter>>

    @GET("/v1/public/characters/{characterId}/comics")
    fun getComicsList(@Path("characterId") id: Long,
                      @Query("offset") offset: Int, @Query("limit") limit: Int)
            : Observable<MarvelResponse<MarvelComics>>
}