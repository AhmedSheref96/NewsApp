package com.el3sas.newsapp

import android.app.Application
import com.el3asas.utils.utils.MyTimberTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.apply {
            plant(MyTimberTree("***********"))
        }
    }
}