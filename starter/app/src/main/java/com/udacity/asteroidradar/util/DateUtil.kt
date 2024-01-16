package com.udacity.asteroidradar.util

import android.annotation.SuppressLint
import com.udacity.asteroidradar.util.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.util.Constants.DEFAULT_END_DATE_DAYS
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

enum class Filter(val startDate: String, val endDate: String) {
    TODAY(DateUtil.getTodayDate(), DateUtil.getTodayDate()),
    WEEK(DateUtil.getTodayDate(), DateUtil.getDaysTo(DEFAULT_END_DATE_DAYS.toLong()))
}

class DateUtil {
    companion object {

        @SuppressLint("WeekBasedYear")
        private fun getDay(time: Long): String =
            SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault()).format(time)

        fun getTodayDate() = getDay(System.currentTimeMillis())

        fun getDaysTo(days: Long) =
            getDay(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(days))
    }
}