package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.domain.Asteroid

@SuppressLint("NotifyDataSetChanged")
class AsteroidsListAdapter : RecyclerView.Adapter<AsteroidsListAdapter.AsteroidViewHolder>() {

    var asteroids: List<Asteroid> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAsteroidBinding.inflate(inflater, parent, false)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroids[position]
        holder.bind(asteroid)
    }

    override fun getItemCount(): Int = asteroids.size


    fun updateAsteroids(asteroids: List<Asteroid>) {
        this.asteroids = asteroids
        notifyDataSetChanged()
    }

    inner class AsteroidViewHolder(private val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val action = MainFragmentDirections.actionShowDetail(asteroids[position])
                    binding.root.findNavController().navigate(action)
                }
            }
        }

        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.tvName.text = asteroid.codeName
            binding.tvDate.text = asteroid.closeApproachDate
        }
    }
}