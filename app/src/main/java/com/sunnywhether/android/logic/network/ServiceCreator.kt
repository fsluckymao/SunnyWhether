package com.sunnywhether.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceCreator {

    private const val ROOT_URL="https://api.caiyunapp.com/"
    val retrofit= Retrofit.Builder()
        .baseUrl(ROOT_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//    val aPPService=retrofit.create(APPService::class.java)
//    val aPPService=retrofit.create<APPService>()

    inline fun <reified T>create():T= retrofit.create(T::class.java)
}