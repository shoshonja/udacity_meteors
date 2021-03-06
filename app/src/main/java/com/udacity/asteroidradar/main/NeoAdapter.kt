package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ListItemNeoBinding

class NeoAdapter : RecyclerView.Adapter<NeoAdapter.NeoViewHolder>() {

    companion object {
        fun from(parent: ViewGroup): NeoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemNeoBinding.inflate(layoutInflater, parent, false)
            return NeoViewHolder(binding)
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

    class NeoViewHolder(val binding: ListItemNeoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ArrayList<Asteroid>, position: Int) {
            binding.listNeoCodename.text = data[position].codename
            binding.listNeoApproachDate.text = data[position].closeApproachDate
            binding.listNeoIsHazardous.setImageResource(
                if (data[position].isPotentiallyHazardous) {
                    R.drawable.ic_status_potentially_hazardous
                } else {
                    R.drawable.ic_status_normal
                }
            )
        }
    }
}