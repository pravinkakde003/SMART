package com.user.smart

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmartApplication : Application() {


    override fun onCreate() {
        super.onCreate()

    }
}