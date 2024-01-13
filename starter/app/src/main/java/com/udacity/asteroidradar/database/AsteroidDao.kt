package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.domain.Asteroid

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroids_table ORDER BY close_approach_date ASC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsteroids(asteroids: List<Asteroid>)

    @Query("SELECT * FROM asteroids_table WHERE close_approach_date = :date")
    fun getTodayAsteroids(date: String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroids_table WHERE close_approach_date BETWEEN :startDay AND :weekAfter ORDER BY close_approach_date ASC")
    fun getWeekAsteroids(startDay: String, weekAfter: String): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroids_table")
    fun deleteAllAsteroid()
}
