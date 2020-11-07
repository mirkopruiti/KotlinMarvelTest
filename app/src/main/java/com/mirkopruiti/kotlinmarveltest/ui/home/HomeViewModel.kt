package com.mirkopruiti.kotlinmarveltest.ui.home

import com.mirkopruiti.kotlinmarveltest.remote.api.ApiService
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelCharacter
import com.mirkopruiti.kotlinmarveltest.ui.base.BaseViewModel

class HomeViewModel(apiService: ApiService) :
        BaseViewModel<MarvelCharacter>(apiService) {

    private var lastSearchTerm = ""

    fun loadMoreCharacters(searchTerm: String? = null) {
        searchTerm?.let {
            if (it != lastSearchTerm) {
                lastSearchTerm = it
                reset()
            }

            callApi(apiService.getCharactersList(offset, count, searchTerm))
            return
        }

        callApi(apiService.getCharactersList(offset, count))
    }
}