package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NeoObject::class], version = 1, exportSchema = false)
abstract class NeoDatabase : RoomDatabase() {
    abstract val neoDatabaseDao: NeoObjectDao

    companion object {

        @Volatile
        private var INSTANCE: NeoDatabase? = null

        fun getInstance(context: Context): NeoDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NeoDatabase::class.java,
                        "neo_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}