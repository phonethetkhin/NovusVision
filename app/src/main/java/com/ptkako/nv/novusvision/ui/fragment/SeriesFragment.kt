package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.series.SeriesAllAdapter
import com.ptkako.nv.novusvision.adapter.series.SeriesNewAdapter
import com.ptkako.nv.novusvision.adapter.series.SeriesPopularAdapter
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
    private lateinit var seriesAllAdapter: SeriesAllAdapter
    private lateinit var seriesPopularAdapter: SeriesPopularAdapter
    private lateinit var seriesNewAdapter: SeriesNewAdapter
    private val binding by fragmentViewBinding(FragmentSeriesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seriesAllAdapter = SeriesAllAdapter(requireContext())
        seriesPopularAdapter = SeriesPopularAdapter(requireContext())
        seriesNewAdapter = SeriesNewAdapter(requireContext())
        homeViewModel.getFinishLiveData().observe(viewLifecycleOwner, Observer {
            Log.d("itinc", it.toString())
            if (it != null && it == 3) {
                binding.pgbSeries.visibility = View.GONE
                binding.nsvSeries.visibility = View.VISIBLE
            }
        })
        observePopularSeries()
        setBinding()
    }

    private fun setBinding() = with(binding)
    {
        imbPopularSeries.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbNewSeries.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbAllSeries.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
    }

    private fun observePopularSeries() {
        homeViewModel.getPopularSeriesListLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                homeViewModel.setFinishLiveData(1)
                observeNewSeries()
                Log.d("livedata", "observePopular: $it")
                seriesPopularAdapter.submitList(it)
                binding.rcvPopularSeries.setHasFixedSize(true)
                binding.rcvPopularSeries.adapter = seriesPopularAdapter
            }
        })
    }

    private fun observeNewSeries() {
        homeViewModel.getNewSeriesListLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                homeViewModel.setFinishLiveData(2)
                observeAllSeries()
                Log.d("livedata", "observeNew: $it")
                seriesNewAdapter.submitList(it)
                binding.rcvNewSeries.setHasFixedSize(true)
                binding.rcvNewSeries.adapter = seriesNewAdapter
            }
        })
    }

    private fun observeAllSeries() {
        homeViewModel.getAllSeriesListLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                homeViewModel.setFinishLiveData(3)
                Log.d("livedata", "observeAll: $it")
                seriesAllAdapter.submitList(it)
                binding.rcvAllSeries.setHasFixedSize(true)
                binding.rcvAllSeries.adapter = seriesAllAdapter
            }
        })
    }
}