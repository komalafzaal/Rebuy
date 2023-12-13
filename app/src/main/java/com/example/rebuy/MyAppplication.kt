package com.example.rebuy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyAppplication:Application() {

    override fun onCreate() {
        super.onCreate()
    }

}