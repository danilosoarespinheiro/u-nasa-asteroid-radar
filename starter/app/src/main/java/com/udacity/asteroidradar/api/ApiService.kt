package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.util.Constants.API_KEY
import com.udacity.asteroidradar.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): String

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String = API_KEY): PictureOfDay

    object Network {
        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        private val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL).build()

        val service: ApiService by lazy { retrofit.create(ApiService::class.java) }

        suspend fun getPictureOfTheDay() = service.getPictureOfTheDay(API_KEY)
    }
}