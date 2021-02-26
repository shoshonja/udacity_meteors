package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NeoObjectDao {

    @Insert
    suspend fun insert(neoObject: NeoObject)

    @Query("SELECT * from neo_object_table WHERE id = :neoId")
    suspend fun get(neoId: Double)

    @Query("SELECT * from neo_object_table WHERE date = :dateFormatted")
    suspend fun getAllValid(dateFormatted: String)

}