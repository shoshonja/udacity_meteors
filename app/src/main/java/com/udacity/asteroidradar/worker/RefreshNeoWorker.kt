package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.NeoDatabase
import com.udacity.asteroidradar.repository.NasaRepository
import retrofit2.HttpException

class RefreshNeoWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "fresWeeklyNeos"
    }

    override suspend fun doWork(): Result {
        val database = NeoDatabase.getInstance(applicationContext)
        val repository = NasaRepository(database.neoDatabaseDao)

        return try {
            repository.refreshNeoList()
            Result.success()
        } catch (e: HttpException){
            Result.retry()
        }
    }
}