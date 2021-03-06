package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class NeoAdapter : RecyclerView.Adapter<NeoAdapter.NeoViewHolder>() {

    var data = ArrayList<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NeoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.random, parent, false) as  TextView

        return NeoViewHolder(view)
    }

    override fun onBindViewHolder(holder: NeoViewHolder, position: Int) {
        val name = data[position].codename
        val date = data[position].closeApproachDate
        val isHazardous = data[position].isPotentiallyHazardous

//        holder.codeName.text = name
//        holder.approachDate.text = date
        holder.textView.text = name
    }

    override fun getItemCount(): Int {
        return data.size
    }

//    class NeoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val codeName = itemView.findViewById(R.id.list_neo_codename) as TextView
//        val approachDate = itemView.findViewById(R.id.list_neo_approach_date) as TextView
//    }

    class NeoViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView){

    }

}