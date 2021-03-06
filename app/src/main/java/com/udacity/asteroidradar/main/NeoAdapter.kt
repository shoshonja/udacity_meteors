package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class NeoAdapter : RecyclerView.Adapter<NeoAdapter.NeoViewHolder>() {

    companion object {
        fun from(parent: ViewGroup): NeoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_neo, parent, false)
            return NeoViewHolder(view)
        }
    }

    var data = ArrayList<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NeoViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: NeoViewHolder, position: Int) {
        holder.bind(data, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class NeoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val codeName = itemView.findViewById(R.id.list_neo_codename) as TextView
        private val approachDate = itemView.findViewById(R.id.list_neo_approach_date) as TextView
        private val hazardousIcon = itemView.findViewById(R.id.list_neo_is_hazardous) as ImageView

        fun bind(data: ArrayList<Asteroid>, position: Int) {
            val name = data[position].codename
            val date = data[position].closeApproachDate
            val isHazardous = data[position].isPotentiallyHazardous

            codeName.text = name
            approachDate.text = date
            hazardousIcon.setImageResource(
                if (isHazardous) {
                    R.drawable.ic_status_potentially_hazardous
                } else {
                    R.drawable.ic_status_normal
                }
            )
        }

    }
}