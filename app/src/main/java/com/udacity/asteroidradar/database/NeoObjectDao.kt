package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NeoObjectDao {

    @Insert
    suspend fun insert(neoObject: NeoObject)

    @Query("SELECT * from neo_object_table WHERE id = :neoId")
    suspend fun get(neoId: Double): NeoObject?

    @Query("SELECT * from neo_object_table WHERE close_approach_date = :today")
     fun getTodaysNeo(today: String): LiveData<List<NeoObject?>>
     //if this is suspend, build fails
}