package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.movie.MoviesAdapter
import com.ptkako.nv.novusvision.databinding.FragmentHomeBinding
import com.ptkako.nv.novusvision.ui.activity.EntireListActivity
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.viewmodel.HomeViewModel
import fragmentViewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI


class HomeFragment : Fragment(R.layout.fragment_home), DIAware {
    override val di: DI by closestDI()
    private val homeViewModel: HomeViewModel by kodeinViewModel()
    private val binding by fragmentViewBinding(FragmentHomeBinding::bind)
    private lateinit var trendingAdapter: MoviesAdapter
    private lateinit var continueWatchingAdapter: MoviesAdapter
    private lateinit var newMoviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trendingAdapter = MoviesAdapter(requireContext())
        continueWatchingAdapter = MoviesAdapter(requireContext())
        newMoviesAdapter = MoviesAdapter(requireContext())

        setBinding()

        homeViewModel.movieListLiveData().observe(viewLifecycleOwner, Observer {

            when (homeViewModel.dataStatus) {
                1 -> {
                    trendingAdapter.submitList(it)
                    homeViewModel.getMovesList(2)
                }
                2 -> {
                    continueWatchingAdapter.submitList(it)
                    homeViewModel.getMovesList(3)
                }
                3 -> {
                    newMoviesAdapter.submitList(it)
                    homeViewModel.pgbHome = View.GONE
                    homeViewModel.nsvHome = View.VISIBLE
                    displayView()
                    homeViewModel.dataStatus = 1
                }
            }
        })

    }

    private fun setBinding() = with(binding)
    {
        rcvTrending.setHasFixedSize(true)
        rcvTrending.adapter = trendingAdapter

        rcvContinueWatching.setHasFixedSize(true)
        rcvContinueWatching.adapter = continueWatchingAdapter

        rcvNewMovies.setHasFixedSize(true)
        rcvNewMovies.adapter = newMoviesAdapter

        rcvRecommended.setHasFixedSize(true)
        // rcvRecommended.adapter = newMoviesAdapter

        displayView()

        imbTrending.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbContinueWatching.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbNewMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbRecommended.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
    }

    private fun displayView() {
        binding.pgbHome.visibility = homeViewModel.pgbHome
        binding.nsvHome.visibility = homeViewModel.nsvHome
    }

}