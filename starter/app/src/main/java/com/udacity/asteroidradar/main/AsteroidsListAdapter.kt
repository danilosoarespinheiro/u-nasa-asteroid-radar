package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.domain.Asteroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AsteroidListAdapter(private val itemClickListener: AsteroidItemOnClickListener) :
    ListAdapter<Asteroid, AsteroidListAdapter.AsteroidsViewHolder>(AsteroidsViewHolder.DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidsViewHolder {
        return AsteroidsViewHolder(
            ItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun adapterSubmitList(list: List<Asteroid>?) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Main) { submitList(list) }
        }
    }

    override fun onBindViewHolder(holder: AsteroidsViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { itemClickListener.onClick(item) }
        holder.bind(item)
    }

    class AsteroidsViewHolder(private val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.tvName.text = asteroid.codeName
            binding.tvDate.text = asteroid.closeApproachDate
            binding.executePendingBindings()
        }

        class DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
            override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean =
                oldItem == newItem
        }
    }

    class AsteroidItemOnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}
