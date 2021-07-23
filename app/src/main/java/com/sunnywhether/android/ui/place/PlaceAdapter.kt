package com.sunnywhether.android.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sunnywhether.android.R
import com.sunnywhether.android.logic.Repository
import com.sunnywhether.android.logic.dao.PlaceDao
import com.sunnywhether.android.logic.model.Place
import com.sunnywhether.android.ui.weather.WeatherActivity

class PlaceAdapter(private val fragment:PlaceFragment,val placeList:List<Place>): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val placeName:TextView=view.findViewById(R.id.placeName)
        val placeAddress:TextView=view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        val viewHolder=ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position=viewHolder.absoluteAdapterPosition
            val place=placeList[position]
            val lng=place.location.lng
            val lat=place.location.lat
            val intent= Intent(parent.context,WeatherActivity::class.java)
            intent.putExtra("lng",lng)
            intent.putExtra("lat",lat)
            intent.putExtra("placeName",place.name)
            PlaceDao.savePlace(place)
            fragment.startActivity(intent)
            fragment.activity?.finish()

            //Toast.makeText(fragment.activity,"${place.name}:::::${viewHolder.bindingAdapterPosition}",Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place=placeList[position]
        holder.placeName.text=place.name
        holder.placeAddress.text=place.address
    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}