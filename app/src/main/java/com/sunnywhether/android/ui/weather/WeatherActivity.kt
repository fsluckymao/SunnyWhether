package com.sunnywhether.android.ui.weather

import android.annotation.SuppressLint
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sunnywhether.android.R
import com.sunnywhether.android.SunnyWeatherApplication
import com.sunnywhether.android.logic.model.Weather
import com.sunnywhether.android.logic.model.getSky
import org.w3c.dom.Text

class WeatherActivity : AppCompatActivity() {
    val viewModel by lazy{
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        if(viewModel.locationLng.isEmpty()){viewModel.locationLng= intent.getStringExtra("lng")?:""}
        if(viewModel.locationLat.isEmpty()){viewModel.locationLat= intent.getStringExtra("lat")?:""}
        if(viewModel.placeName.isEmpty()){viewModel.placeName= intent.getStringExtra("placeName")?:""}

        viewModel.weatherLiveData.observe(this, Observer {result->
            val weather=result.getOrNull()
            if(weather!=null){
                Log.d(SunnyWeatherApplication.TAG, "weather！=null")
                showWeatherInfo(weather)
            }else{
                Toast.makeText(this,"无法获取天气信息",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        Log.d(SunnyWeatherApplication.TAG, "${viewModel.locationLng}:::${viewModel.locationLat}")
        viewModel.refreshLocation(viewModel.locationLng,viewModel.locationLat)
    }

    @SuppressLint("SetTextI18n")
    private fun showWeatherInfo(weather: Weather) {
        //now.xml
        findViewById<TextView>(R.id.textViewPlaceName).text=viewModel.placeName
        findViewById<TextView>(R.id.currentTemp).text=weather.realtime.temperature.toString()
        findViewById<TextView>(R.id.currentSky).text= getSky(weather.realtime.skycon).info

        findViewById<TextView>(R.id.currentAQI).text="空气指数 ${weather.realtime.airQuality.aqi.chn.toInt()}"

        //life_index.xml
        findViewById<TextView>(R.id.coldRiskText).text= weather.daily.lifeIndex.coldRisk[0].desc
        findViewById<TextView>(R.id.ultravioletText).text= weather.daily.lifeIndex.ultraviolet[0].desc
        findViewById<TextView>(R.id.dressingText).text= weather.daily.lifeIndex.dressing[0].desc
        findViewById<TextView>(R.id.carwashingText).text= weather.daily.lifeIndex.carWashing[0].desc

        val forecastLayout=findViewById<LinearLayout>(R.id.forecastLayout)
        forecastLayout.removeAllViews()
        for(i in 0 until weather.daily.skycon.size){
            val view=LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false)
            view.findViewById<TextView>(R.id.temperateInfo).text="${weather.daily.temperature[i].min}~${weather.daily.temperature[i].max}"
            val sky= getSky(weather.daily.skycon[i].value)
            view.findViewById<TextView>(R.id.skyInfo).text=sky.info
            view.findViewById<ImageView>(R.id.skyIcon).setImageResource(sky.icon)
            view.findViewById<TextView>(R.id.dateInfo).text=weather.daily.skycon[i].date.toString()
            forecastLayout.addView(view)
        }



    }
}