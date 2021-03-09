package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ListItemNeoBinding

class NeoAdapter(private val clickListener: NeoClickListener) : RecyclerView.Adapter<NeoViewHolder>() {

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
        holder.bind(clickListener, data, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class NeoViewHolder(private val binding: ListItemNeoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ArrayList<Asteroid>, position: Int) {
        binding.asteroid = data[position]
        binding.executePendingBindings()
    }

    fun bind(clickListener: NeoClickListener, data: ArrayList<Asteroid>, position: Int){
        binding.asteroid = data[position]
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
}

class NeoClickListener(val clickListener: (asteroidId: Long) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid.id)
}