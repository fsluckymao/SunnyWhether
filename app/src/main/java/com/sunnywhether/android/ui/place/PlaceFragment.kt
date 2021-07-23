package com.sunnywhether.android.ui.place

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunnywhether.android.R
import com.sunnywhether.android.SunnyWeatherApplication
import com.sunnywhether.android.logic.dao.PlaceDao
import com.sunnywhether.android.ui.weather.WeatherActivity

class PlaceFragment:Fragment() {

    val viewModel by lazy{
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }
    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(SunnyWeatherApplication.TAG, PlaceDao.isPlaceSaved().toString())
        if(PlaceDao.isPlaceSaved()){
            Log.d(SunnyWeatherApplication.TAG, "isSavedPlaced")
            val place=PlaceDao.getSavedPlace()
            val intent= Intent(this.activity,WeatherActivity::class.java)
            intent.putExtra("lng",place.location.lng)
            intent.putExtra("lat",place.location.lat)
            intent.putExtra("placeName",place.name)
            startActivity(intent)
            activity?.finish()
            return
        }

        val layoutManager=LinearLayoutManager(activity)
        val recyclerView:RecyclerView=requireView().findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.layoutManager =layoutManager
        adapter=PlaceAdapter(this,viewModel.placeList)
        recyclerView.adapter =adapter
        val bgImageView= requireView().findViewById<ImageView>(R.id.imageView)
        view?.findViewById<EditText>(R.id.editPlace)?.addTextChangedListener { editable->
            val content=editable.toString()
            if(content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                recyclerView.visibility=View.GONE
                bgImageView.visibility=View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result->
            val places=result.getOrNull()
            if(places!=null){
                recyclerView.visibility=View.VISIBLE
                bgImageView.visibility=View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
//                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"未查到该地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }
}