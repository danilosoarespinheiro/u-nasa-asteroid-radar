package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.util.AsteroidApiFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: android.app.Application) : AndroidViewModel(application) {

    private val database = AsteroidsDatabase.getDatabaseInstance(application)
    private val repository = AsteroidRepository(database)
    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    private val fromListToItemDetail = MutableLiveData<Asteroid?>()

    val asteroids = repository.asteroids
    val pictureOfTheDay: LiveData<PictureOfDay> get() = _pictureOfTheDay

    init {
        refreshAsteroids()
        getPictureOfTheDay()
    }

    fun fromListToItemDetail(item: Asteroid) {
        fromListToItemDetail.value = item
    }

    fun cancelFromListToItemDetail() {
        fromListToItemDetail.value = null
    }

    private fun getPictureOfTheDay() = viewModelScope.launch(Dispatchers.Main) {
        try {
            _pictureOfTheDay.value = repository.getPictureOfDay()
        } catch (exception: Exception) {
            Log.e("PictureOfTheDayError", exception.stackTraceToString())
        }
    }

    private fun refreshAsteroids() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.refreshAsteroids()
        } catch (exception: Exception) {
            Log.e("RefreshAsteroidsError", exception.stackTraceToString())
        }
    }

    private val asteroidItemList = MutableLiveData(AsteroidApiFilter.SHOW_WEEK)

    val asteroidsList = asteroidItemList.switchMap {
        when (it) {
            AsteroidApiFilter.SHOW_TODAY -> repository.asteroidsOfToday
            AsteroidApiFilter.SHOW_WEEK -> repository.asteroidsOfWeek
            else -> repository.asteroids
        }
    }

    fun selectFilter(filter: AsteroidApiFilter) {
        asteroidItemList.value = filter
    }
}