package com.sunnywhether.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication:Application() {
    companion object{
        const val TOKEN="rR5JG7BEbiYm7Wuf"
        const val TAG="MCT_WEATHER_TEST"
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}