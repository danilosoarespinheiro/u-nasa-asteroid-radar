package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

@Suppress(
    "DeprecatedCall" +
            "ableAddReplaceWith"
)
class MainFragment : Fragment() {

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

        viewModel.asteroids.observe(viewLifecycleOwner) {
            adapter.updateAsteroids(it)
        }

        viewModel.pictureOfTheDay.observe(viewLifecycleOwner) {
            it?.let {
                if (it.mediaType == "image") {
                    Picasso.with(context)
                        .load(it.url)
                        .into(binding.activityMainImageOfTheDay)
                    Log.d("Picasso", "Carregando imagem da URL: ${it.url}")
                }
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
