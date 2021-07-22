package com.sunnywhether.android.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceRespose(val status:String,val places:List<Place>)
data class Place(val name:String,val location:Location,@SerializedName("formatted_address") val address:String)
data class Location(val lng:Double,val lat:Double)