package com.udacity.asteroidradar

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class Application : Application() {

    private val scope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() = scope.launch { setupRecurringWork() }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply { setRequiresDeviceIdle(true) }
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<DataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            DataWorker.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, repeatingRequest
        )
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }
}