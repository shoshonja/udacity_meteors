package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
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

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val refreshNeoList = PeriodicWorkRequestBuilder<RefreshNeoWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager
            .getInstance()
            .enqueueUniquePeriodicWork(
            RefreshNeoWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            refreshNeoList
        )
    }
}