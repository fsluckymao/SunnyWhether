package com.sunnywhether.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.sunnywhether.android.ui.weather.WeatherActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TESTTEST", "onCreate: !!!")
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent=Intent(this,WeatherActivity::class.java)
            startActivity(intent)
        }
    }
}