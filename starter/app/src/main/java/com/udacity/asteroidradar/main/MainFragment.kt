package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        private const val MEDIA_TYPE = "image"
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: FragmentMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = AsteroidsListAdapter()
        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner) { adapter.getTodayAsteroids(it) }

        viewModel.pictureOfTheDay.observe(viewLifecycleOwner) {
            it?.let {
                if (it.mediaType == MEDIA_TYPE) {
                    Picasso.with(context).load(it.url).into(binding.activityMainImageOfTheDay)
                    binding.activityMainImageOfTheDay.contentDescription = it.title
                }
            }
        }
        return binding.root
    }
}
