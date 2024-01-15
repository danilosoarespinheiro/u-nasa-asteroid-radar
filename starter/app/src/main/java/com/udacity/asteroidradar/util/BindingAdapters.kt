package com.udacity.asteroidradar.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.domain.Asteroid

@BindingAdapter("statusIcon")
fun ImageView.bindAsteroidStatusImage(asteroid: Asteroid) {
    if (asteroid.isPotentiallyHazardous) setImageResource(R.drawable.ic_status_potentially_hazardous)
    else setImageResource(R.drawable.ic_status_normal)
}

@BindingAdapter("asteroidStatusImage")
fun ImageView.bindDetailsStatusImage(isHazardous: Boolean) {
    if (isHazardous) setImageResource(R.drawable.asteroid_hazardous)
    else setImageResource(R.drawable.asteroid_safe)
}

@BindingAdapter("astronomicalUnitText")
fun TextView.bindTextViewToAstronomicalUnit(number: Double) {
    text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun TextView.bindTextViewToKmUnit(number: Double) {
    text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun TextView.bindTextViewToDisplayVelocity(number: Double) {
    text = String.format(context.getString(R.string.km_s_unit_format), number)
}
