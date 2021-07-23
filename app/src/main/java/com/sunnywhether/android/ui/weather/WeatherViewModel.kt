package com.sunnywhether.android.ui.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnywhether.android.SunnyWeatherApplication
import com.sunnywhether.android.logic.Repository
import com.sunnywhether.android.logic.model.Location

class WeatherViewModel:ViewModel() {
    private val locationLiveData=MutableLiveData<Location>()

    var locationLng=""
    var locationLat=""
    var placeName=""


    val weatherLiveData=Transformations.switchMap(locationLiveData){location->

        Repository.refreshWeather(location.lng,location.lat)
    }


    fun refreshLocation(lng:String,lat:String){
        locationLiveData.value= Location(lng,lat)
    }

}