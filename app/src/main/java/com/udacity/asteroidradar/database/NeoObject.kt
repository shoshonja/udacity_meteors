package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "neo_object_table")
data class NeoObject(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var neoId: Double,

    @ColumnInfo(name = "absolute_magnitude")
    var absoluteMagnitude: Double,

    @ColumnInfo(name = "estimated_diameter_max")
    var estimatedDiameterMax : Double,

    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
    var potentiallyHazardous : Boolean,

    //close_approach_data -> relative_velocity -> kilometers_per_second
    @ColumnInfo(name = "relative_velocity")
    var relativeVelocity : Double,

    //close_approach_data -> miss_distance -> astronomical
    @ColumnInfo(name = "miss_distance")
    var missDistance: Double,

    var date: String



)