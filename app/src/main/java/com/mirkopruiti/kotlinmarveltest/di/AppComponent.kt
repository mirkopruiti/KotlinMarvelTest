package com.mirkopruiti.kotlinmarveltest.di

import com.mirkopruiti.kotlinmarveltest.ui.details.DetailsComicsFragment
import com.mirkopruiti.kotlinmarveltest.ui.home.HomeFragment
import com.mirkopruiti.kotlinmarveltest.ui.home.HomeSearchFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(homeFragment: HomeFragment)
    fun inject(homeFragment: HomeSearchFragment)
    fun inject(detailsComicsFragment: DetailsComicsFragment)
}