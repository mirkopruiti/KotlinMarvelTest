package com.mirkopruiti.kotlinmarveltest.ui.details

import com.mirkopruiti.kotlinmarveltest.remote.api.ApiService
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelComics
import com.mirkopruiti.kotlinmarveltest.ui.base.BaseViewModel

class DetailsComicsViewModel(apiService: ApiService) : BaseViewModel<MarvelComics>(apiService) {

    fun loadMore(characterId: Long) {
        callApi(apiService.getComicsList(characterId, offset, count))
    }

}