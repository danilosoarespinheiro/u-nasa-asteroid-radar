package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.api.ApiService.Network.getPictureOfTheDay
import com.udacity.asteroidradar.api.ApiService.Network.service
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.util.Filter.TODAY
import com.udacity.asteroidradar.util.Filter.WEEK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidsDatabase) {

    val asteroids: LiveData<List<Asteroid>> = database.asteroidDao.getAllAsteroids()

    val asteroidsOfToday: LiveData<List<Asteroid>> =
        database.asteroidDao.getTodayAsteroids(TODAY.startDate)

    val asteroidsOfWeek: LiveData<List<Asteroid>> =
        database.asteroidDao.getWeekAsteroids(WEEK.startDate, WEEK.endDate)

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val result = service.getAsteroids("", "")
            val resultJsonObject =
                parseAsteroidsJsonResult(JSONObject(result))
            database.asteroidDao.insertAllAsteroids(resultJsonObject)
        }
    }

    suspend fun getPictureOfDay(): PictureOfDay {
        lateinit var pictureOfTheDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfTheDay = getPictureOfTheDay()
        }
        return pictureOfTheDay
    }

}