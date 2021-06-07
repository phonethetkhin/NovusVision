package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.SeriesAdapter
import com.ptkako.nv.novusvision.databinding.FragmentSeriesBinding
import com.ptkako.nv.novusvision.ui.activity.EntireListActivity
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.viewmodel.HomeViewModel
import fragmentViewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class SeriesFragment : Fragment(R.layout.fragment_series), DIAware {
    override val di: DI by closestDI()
    private val homeViewModel: HomeViewModel by kodeinViewModel()
    private lateinit var seriesPopularAdapter: SeriesAdapter
    private lateinit var seriesNewAdapter: SeriesAdapter
    private lateinit var seriesAllAdapter: SeriesAdapter
    private val binding by fragmentViewBinding(FragmentSeriesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seriesPopularAdapter = SeriesAdapter(requireContext())
        seriesNewAdapter = SeriesAdapter(requireContext())
        seriesAllAdapter = SeriesAdapter(requireContext())
        setVisibility()
        observeSeries()
        setBinding()
    }

    private fun setBinding() = with(binding)
    {
        rcvPopularSeries.setHasFixedSize(true)
        rcvPopularSeries.adapter = seriesPopularAdapter

        rcvNewSeries.setHasFixedSize(true)
        rcvNewSeries.adapter = seriesNewAdapter

        rcvAllSeries.setHasFixedSize(true)
        rcvAllSeries.adapter = seriesAllAdapter

        imbPopularSeries.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbNewSeries.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbAllSeries.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }

        srlSeries.setOnRefreshListener {
            srlSeries.isRefreshing = true
            homeViewModel.getSeriesList(srlSeries)
        }
    }

    private fun observeSeries() {
        homeViewModel.getSeriesListLiveData().observe(viewLifecycleOwner, {
            seriesPopularAdapter.submitList(it[0])
            seriesNewAdapter.submitList(it[1])
            seriesAllAdapter.submitList(it[2])
            setVisibility()
        })
    }

    private fun setVisibility() = with(binding) {
        pgbSeries.visibility = homeViewModel.pgbSeries
        nsvSeries.visibility = homeViewModel.nsvSeries
    }
}