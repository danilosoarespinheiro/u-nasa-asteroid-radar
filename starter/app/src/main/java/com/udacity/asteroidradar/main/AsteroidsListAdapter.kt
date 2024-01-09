package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidsListAdapter(private val itemClickListener: AsteroidItemOnClickListener) :
    ListAdapter<Asteroid, AsteroidsListAdapter.AsteroidsViewHolder>(AsteroidsViewHolder.DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidsViewHolder {
        return AsteroidsViewHolder(
            ItemAsteroidBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AsteroidsViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(item)
        }
        holder.bind(item)
    }

    class AsteroidsViewHolder(private val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid) {
            binding.asteroid = item
            binding.executePendingBindings()
        }

        class DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
            override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem == newItem
            }
        }
    }

    class AsteroidItemOnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}