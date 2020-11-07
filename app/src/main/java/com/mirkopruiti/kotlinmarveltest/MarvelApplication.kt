package com.mirkopruiti.kotlinmarveltest

import android.app.Application
import com.mirkopruiti.kotlinmarveltest.di.AppComponent
import com.mirkopruiti.kotlinmarveltest.di.AppModule
import com.mirkopruiti.kotlinmarveltest.di.DaggerAppComponent

class MarvelApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}