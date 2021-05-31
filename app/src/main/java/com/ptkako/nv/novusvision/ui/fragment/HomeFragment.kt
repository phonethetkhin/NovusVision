package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.movie.MoviesAllAdapter
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
    private lateinit var moviesAllAdapter: MoviesAllAdapter
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAllAdapter = MoviesAllAdapter(requireContext())
        db = Firebase.firestore
/*
        val moviesList = ArrayList<MoviesModel>()
        moviesList.add(MoviesModel(1, R.drawable.captain_marvel, "Captain Marvel"))
        moviesList.add(MoviesModel(2, R.drawable.game_of_throne, "Captain Marvel"))
        moviesList.add(MoviesModel(3, R.drawable.spiderman, "Captain Marvel"))
        moviesList.add(MoviesModel(4, R.drawable.superman, "Captain Marvel"))
        moviesList.add(MoviesModel(5, R.drawable.warcraft, "Captain Marvel"))

        moviesAdapter.submitList(moviesList)*/
        setBinding()

    }

    private fun setBinding() = with(binding)
    {
        rcvTrending.setHasFixedSize(true)
        rcvTrending.adapter = moviesAllAdapter

        rcvContinueWatching.setHasFixedSize(true)
        rcvContinueWatching.adapter = moviesAllAdapter

        rcvNewMovies.setHasFixedSize(true)
        rcvNewMovies.adapter = moviesAllAdapter

        rcvRecommended.setHasFixedSize(true)
        rcvRecommended.adapter = moviesAllAdapter

        imbTrending.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbContinueWatching.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbNewMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbRecommended.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
    }


}