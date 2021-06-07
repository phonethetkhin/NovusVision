package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.MoviesAdapter
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
        setVisibility()
        observeHome()
        setBinding()

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

        imbTrending.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbContinueWatching.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbNewMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbRecommended.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        srlHome.setOnRefreshListener {
            srlHome.isRefreshing = true
            homeViewModel.getHomeList(srlHome)
        }
    }

    private fun observeHome() {
        homeViewModel.getHomeListLiveData().observe(viewLifecycleOwner, {
            trendingAdapter.submitList(it[0])
            newMoviesAdapter.submitList(it[1])
            continueWatchingAdapter.submitList(it[2])
            setVisibility()
        })
    }

    private fun setVisibility() {
        binding.pgbHome.visibility = homeViewModel.pgbHome
        binding.nsvHome.visibility = homeViewModel.nsvHome
    }

}