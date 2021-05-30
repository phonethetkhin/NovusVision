@file:Suppress("UNCHECKED_CAST")

package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.movies.MoviesAllAdapter
import com.ptkako.nv.novusvision.adapter.movies.MoviesNewAdapter
import com.ptkako.nv.novusvision.adapter.movies.MoviesPopularAdapter
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
    private lateinit var moviesAllAdapter: MoviesAllAdapter
    private lateinit var moviesPopularAdapter: MoviesPopularAdapter
    private lateinit var moviesNewAdapter: MoviesNewAdapter
    private val binding by fragmentViewBinding(FragmentMovieBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAllAdapter = MoviesAllAdapter(requireContext())
        moviesPopularAdapter = MoviesPopularAdapter(requireContext())
        moviesNewAdapter = MoviesNewAdapter(requireContext())
        homeViewModel.getFinishLiveData().observe(viewLifecycleOwner, Observer {
            Log.d("itinc", it.toString())
            if (it != null && it == 3) {
                binding.pgbMovie.visibility = View.GONE
                binding.nsvMovie.visibility = View.VISIBLE
            }
        })
        observePopularMovie()
        setBinding()


    }

    private fun setBinding() = with(binding)
    {
        imbPopularMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbNewMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbAllMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
    }

    private fun observePopularMovie() {
        homeViewModel.getPopularMovieLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                homeViewModel.setFinishLiveData(1)
                observeNewMovie()
                Log.d("livedata", "observePopular: $it")
                moviesPopularAdapter.submitList(it)
                binding.rcvPopularMovies.setHasFixedSize(true)
                binding.rcvPopularMovies.adapter = moviesPopularAdapter
            }
        })
    }

    private fun observeNewMovie() {
        homeViewModel.getNewMovieListLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                homeViewModel.setFinishLiveData(2)
                observeAllMovie()
                Log.d("livedata", "observeNew: $it")
                moviesNewAdapter.submitList(it)
                binding.rcvNewMovies.setHasFixedSize(true)
                binding.rcvNewMovies.adapter = moviesNewAdapter
            }
        })
    }

    private fun observeAllMovie() {
        homeViewModel.getAllMovieListLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                homeViewModel.setFinishLiveData(3)
                Log.d("livedata", "observeAll: $it")
                moviesAllAdapter.submitList(it)
                binding.rcvAllMovies.setHasFixedSize(true)
                binding.rcvAllMovies.adapter = moviesAllAdapter
            }
        })
    }
}