package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidsDatabase.Companion.getDatabaseInstance
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class DataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "DataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabaseInstance(applicationContext)
        val repository = AsteroidRepository(database)
        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}