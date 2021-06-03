@file:Suppress("UNCHECKED_CAST")

package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.MoviesAdapter
import com.ptkako.nv.novusvision.databinding.FragmentMovieBinding
import com.ptkako.nv.novusvision.ui.activity.EntireListActivity
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.viewmodel.HomeViewModel
import fragmentViewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class MovieFragment : Fragment(R.layout.fragment_movie), DIAware {
    override val di: DI by closestDI()
    private val homeViewModel: HomeViewModel by kodeinViewModel()
    private lateinit var moviesAllAdapter: MoviesAdapter
    private lateinit var moviesPopularAdapter: MoviesAdapter
    private lateinit var moviesNewAdapter: MoviesAdapter
    private val binding by fragmentViewBinding(FragmentMovieBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAllAdapter = MoviesAdapter(requireContext())
        moviesPopularAdapter = MoviesAdapter(requireContext())
        moviesNewAdapter = MoviesAdapter(requireContext())
        setVisibility()
        observeMovie()
        setBinding()
    }

    private fun setBinding() = with(binding)
    {
        imbPopularMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbNewMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbAllMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
    }

    private fun observeMovie() {
        homeViewModel.getMovieListLiveData().observe(viewLifecycleOwner, Observer {

            moviesPopularAdapter.submitList(it[0])
            binding.rcvPopularMovies.setHasFixedSize(true)
            binding.rcvPopularMovies.adapter = moviesPopularAdapter

            moviesNewAdapter.submitList(it[1])
            binding.rcvNewMovies.setHasFixedSize(true)
            binding.rcvNewMovies.adapter = moviesNewAdapter

            moviesAllAdapter.submitList(it[2])
            binding.rcvAllMovies.setHasFixedSize(true)
            binding.rcvAllMovies.adapter = moviesAllAdapter
            setVisibility()

        })
    }
    private fun setVisibility() {
        binding.pgbMovie.visibility = homeViewModel.pgbMovie
        binding.nsvMovie.visibility = homeViewModel.nsvMovie
    }
}