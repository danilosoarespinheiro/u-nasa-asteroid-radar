package com.udacity.asteroidradar.util

import android.annotation.SuppressLint
import com.udacity.asteroidradar.util.Constants.DEFAULT_END_DATE_DAYS
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

enum class AsteroidApiFilter(val value: String) {
    SHOW_TODAY("Today"), SHOW_WEEK("Week"), SHOW_SAVED("Saved")
}

enum class Filter(val startDate: String, val endDate: String) {
    TODAY(DateUtil.getTodayDate(), DateUtil.getTodayDate()),
    WEEK(DateUtil.getTodayDate(), DateUtil.getDaysTo(DEFAULT_END_DATE_DAYS.toLong()))
}

class DateUtil {
    companion object {

        @SuppressLint("WeekBasedYear")
        private fun getDay(time: Long): String =
            SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(
                time
            )

        fun getTodayDate(): String {
            val currentTime = System.currentTimeMillis()
            return getDay(currentTime)
        }

        fun getDaysTo(days: Long): String {
            val diffDaysTo = TimeUnit.DAYS.toMillis(days)
            val time = System.currentTimeMillis() + diffDaysTo
            return getDay(time)
        }
    }
}