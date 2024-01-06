package com.udacity.asteroidradar.main

import com.udacity.asteroidradar.Asteroid

class AsteroidsListAdapter(private val itemClickListener: AsteroidItemOnClickListener) {

    class AsteroidItemOnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}