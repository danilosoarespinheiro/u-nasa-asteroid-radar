package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: android.app.Application) : AndroidViewModel(application) {

    private val database = AsteroidsDatabase.getDatabaseInstance(application)
    private val repository = AsteroidRepository(database)
    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()

    private val _currentAsteroids = MutableLiveData<List<Asteroid>>()
    val currentAsteroids: LiveData<List<Asteroid>> get() = _currentAsteroids
    val pictureOfTheDay: LiveData<PictureOfDay> get() = _pictureOfTheDay

    companion object {
        private const val PICTURE_OF_THE_DAY_TAG_ERROR = "PictureOfTheDayError"
        private const val REFRESH_ASTEROIDS_TAG_ERROR = "RefreshAsteroidsError"
    }

    init {
        refreshAsteroids()
        getPictureOfTheDay()
    }

    fun asteroidsAll(){
        val asteroidsLiveData = repository.asteroids
        asteroidsLiveData.observeForever { asteroidsList ->
            _currentAsteroids.postValue(asteroidsList)
        }
    }

    fun asteroidsWeek(){
        val asteroidsLiveData = repository.asteroidsOfWeek
        asteroidsLiveData.observeForever { asteroidsList ->
            _currentAsteroids.postValue(asteroidsList)
        }
    }

    fun asteroidsToday(){
        val asteroidsLiveData = repository.asteroidsOfToday
        asteroidsLiveData.observeForever { asteroidsList ->
            _currentAsteroids.postValue(asteroidsList)
        }
    }

    private fun getPictureOfTheDay() = viewModelScope.launch(Dispatchers.Main) {
        try {
            _pictureOfTheDay.value = repository.getPictureOfDay()
        } catch (exception: Exception) {
            Log.e(PICTURE_OF_THE_DAY_TAG_ERROR, exception.stackTraceToString())
        }
    }

    private fun refreshAsteroids() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.refreshAsteroids()
        } catch (exception: Exception) {
            Log.e(REFRESH_ASTEROIDS_TAG_ERROR, exception.stackTraceToString())
        }
    }

}