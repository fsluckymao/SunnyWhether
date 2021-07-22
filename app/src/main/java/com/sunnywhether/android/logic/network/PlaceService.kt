package com.sunnywhether.android.logic.network

import com.sunnywhether.android.SunnyWeatherApplication
import com.sunnywhether.android.logic.model.PlaceRespose
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String):Call<PlaceRespose>
}