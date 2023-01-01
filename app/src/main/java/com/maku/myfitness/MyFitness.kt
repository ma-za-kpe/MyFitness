package com.maku.myfitness

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyFitness : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}