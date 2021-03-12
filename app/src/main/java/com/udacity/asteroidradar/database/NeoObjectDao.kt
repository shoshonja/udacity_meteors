package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NeoObjectDao {

//    @Insert
//    fun insert(neoObject: NeoObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(neoObject: List<NeoObject>)
    //neoObject cannot be vararg if it is List. Build process fails

    @Query("SELECT * from neo_object_table WHERE id = :neoId")
    suspend fun get(neoId: Double): NeoObject?

    @Query("SELECT * from neo_object_table ORDER BY close_approach_date")
    fun getAllNeos(): List<NeoObject>
    //if we return regular list, it will block the UI thread. So we should make it suspend. We need to return the live data
    //if we return the live data, room does database query in the background for us
    //and it will update live data anytime new data is written to the table

    @Query("SELECT * from neo_object_table WHERE close_approach_date = :today")
    fun getDailyNeos(today: String): List<NeoObject>
    //if this is suspend, build fails
}