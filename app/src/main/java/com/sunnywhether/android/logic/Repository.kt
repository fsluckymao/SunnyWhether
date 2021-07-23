package com.sunnywhether.android.logic

import androidx.lifecycle.liveData
import com.sunnywhether.android.logic.model.Place
import com.sunnywhether.android.logic.model.Weather
import com.sunnywhether.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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

    fun refreshWeather(lng:String,lat:String)= liveData(Dispatchers.IO) {
        val result=try{
            coroutineScope{
                val deferredDaily=async {
                    SunnyWeatherNetwork.getDailyWeather(lng,lat)
                }
                val deferredRealtime=async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
                }

                val realtimeResponse=deferredRealtime.await()
                val dailyResponse=deferredDaily.await()

                if(realtimeResponse.status=="ok"&&dailyResponse.status=="ok"){
                    Result.success(Weather(realtimeResponse.result.realtime,dailyResponse.result.daily))
                }else{
                    Result.failure(RuntimeException("realtime response is ${realtimeResponse.status}"+"daily response is ${dailyResponse.status}"))
                }
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }
}