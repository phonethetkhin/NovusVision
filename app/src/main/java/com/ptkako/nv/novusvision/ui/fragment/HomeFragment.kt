package com.ptkako.nv.novusvision.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.MoviesAdapter
import com.ptkako.nv.novusvision.databinding.FragmentHomeBinding
import com.ptkako.nv.novusvision.model.MoviesModel
import fragmentViewBinding


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by fragmentViewBinding(FragmentHomeBinding::bind)
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MoviesAdapter(requireContext())

        val moviesList = ArrayList<MoviesModel>()
        moviesList.add(MoviesModel(1, R.drawable.captain_marvel, "Captain Marvel"))
        moviesList.add(MoviesModel(2, R.drawable.game_of_throne, "Captain Marvel"))
        moviesList.add(MoviesModel(3, R.drawable.spiderman, "Captain Marvel"))
        moviesList.add(MoviesModel(4, R.drawable.superman, "Captain Marvel"))
        moviesList.add(MoviesModel(5, R.drawable.warcraft, "Captain Marvel"))

        moviesAdapter.submitList(moviesList)
        setBinding()

    }

    private fun setBinding() = with(binding)
    {
        rcvTrending.setHasFixedSize(true)
        rcvTrending.adapter = moviesAdapter

        rcvContinueWatching.setHasFixedSize(true)
        rcvContinueWatching.adapter = moviesAdapter

        rcvNewMovies.setHasFixedSize(true)
        rcvNewMovies.adapter = moviesAdapter

        rcvRecommended.setHasFixedSize(true)
        rcvRecommended.adapter = moviesAdapter
    }
}