package com.sunnywhether.android.logic

import androidx.lifecycle.liveData
import com.sunnywhether.android.logic.model.Place
import com.sunnywhether.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import java.lang.Exception
import java.lang.RuntimeException

object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result =try{
            val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
            if(placeResponse.status=="ok"){
                Result.success(placeResponse.places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }

        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}