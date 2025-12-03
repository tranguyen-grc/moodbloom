package com.example.moodbloom

import android.app.Application
import timber.log.Timber

class MoodBloomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}