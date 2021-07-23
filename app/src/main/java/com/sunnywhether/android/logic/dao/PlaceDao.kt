package com.sunnywhether.android.logic.dao

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnywhether.android.SunnyWeatherApplication
import com.sunnywhether.android.logic.model.Place

object PlaceDao {
    fun savePlace(place: Place){
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }
    fun getSavedPlace():Place{
        val placeJson= sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }
    fun isPlaceSaved() = sharedPreferences().contains("place")
    fun sharedPreferences(): SharedPreferences {
        return SunnyWeatherApplication.context.getSharedPreferences("sunny_weather",0)
    }
}