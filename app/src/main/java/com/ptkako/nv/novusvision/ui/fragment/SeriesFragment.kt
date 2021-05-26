package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
    private lateinit var seriesAdapter: SeriesAdapter
    private val binding by fragmentViewBinding(FragmentSeriesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seriesAdapter = SeriesAdapter(requireContext())
        homeViewModel.getSeriesListLiveData().observe(viewLifecycleOwner, Observer {
            seriesAdapter.submitList(it)
            setBinding()
        })
    }

    private fun setBinding() = with(binding)
    {
        rcvPopularSeries.setHasFixedSize(true)
        rcvPopularSeries.adapter = seriesAdapter

        rcvNewSeries.setHasFixedSize(true)
        rcvNewSeries.adapter = seriesAdapter

        rcvAllSeries.setHasFixedSize(true)
        rcvAllSeries.adapter = seriesAdapter

        imbPopularSeries.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbNewSeries.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbAllSeries.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }

    }
}