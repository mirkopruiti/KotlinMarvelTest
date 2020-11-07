package com.mirkopruiti.kotlinmarveltest.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mirkopruiti.kotlinmarveltest.remote.api.ApiService
import com.mirkopruiti.kotlinmarveltest.ui.details.DetailsComicsViewModel
import com.mirkopruiti.kotlinmarveltest.ui.home.HomeViewModel
import javax.inject.Inject

class BaseViewModelFactory @Inject constructor(var apiService: ApiService, ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(apiService) as T
        } else if (modelClass.isAssignableFrom(DetailsComicsViewModel::class.java)) {
            return DetailsComicsViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}