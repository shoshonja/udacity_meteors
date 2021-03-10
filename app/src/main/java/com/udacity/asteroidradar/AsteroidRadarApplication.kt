package com.udacity.asteroidradar

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.udacity.asteroidradar.worker.RefreshNeoWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        setupWorkers()
    }

    private fun setupWorkers() = applicationScope.launch {
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val refreshNeoList = PeriodicWorkRequestBuilder<RefreshNeoWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshNeoWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            refreshNeoList
        )
    }
}