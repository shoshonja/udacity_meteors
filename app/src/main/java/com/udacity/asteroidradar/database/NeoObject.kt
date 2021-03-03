package com.udacity.asteroidradar.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "neo_object_table")
data class NeoObject(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    var neoId: Double,

    @ColumnInfo(name = "code_name")
    var codename: String,

    @ColumnInfo(name = "close_approach_date")
    var closeApproachDate: String,

    @ColumnInfo(name = "absolute_magnitude")
    var absoluteMagnitude: Double,

    @ColumnInfo(name = "estimated_diameter_max")
    var estimatedDiameterMax : Double,

    //close_approach_data -> relative_velocity -> kilometers_per_second
    @ColumnInfo(name = "relative_velocity")
    var relativeVelocity : Double,

    //close_approach_data -> miss_distance -> astronomical
    @ColumnInfo(name = "distance_from_earth")
    var distanceFromEarth: Double,

    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
    var potentiallyHazardous : Boolean,
)