package com.example.unscramble

import android.app.Application
import com.example.unscramble.data.AppContainer

class UnscrambleApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}