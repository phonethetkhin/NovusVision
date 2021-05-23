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
import com.ptkako.nv.novusvision.model.ContentModel
import com.ptkako.nv.novusvision.model.MovieInfoModel
import com.ptkako.nv.novusvision.model.MoviesModel
import com.ptkako.nv.novusvision.ui.activity.EntireListActivity
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.utility.showToast
import com.ptkako.nv.novusvision.viewmodel.HomeViewModel
import fragmentViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class MovieFragment : Fragment(R.layout.fragment_movie), DIAware {
    override val di: DI by closestDI()
    private val homeViewModel: HomeViewModel by kodeinViewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private val binding by fragmentViewBinding(FragmentMovieBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("lifecycle", "onViewCreated")

        moviesAdapter = MoviesAdapter(this)
        setBinding()
        CoroutineScope(Dispatchers.Main).launch {
            homeViewModel.getMovieListLiveData().observe(viewLifecycleOwner, Observer {
                Log.d("lifecycle", "observe")
                Log.d("lifecycle", it.toString())

                when (it) {
                    is Exception -> {
                        requireActivity().showToast(it.localizedMessage)
                        homeViewModel.movieListLiveData.postValue(null)
                    }
                    else -> {
                        moviesAdapter.submitList(it as ArrayList<MoviesModel>)
                    }

                }
            })
        }

    }

    private fun setBinding() = with(binding)
    {
        rcvPopularMovies.setHasFixedSize(true)
        rcvPopularMovies.adapter = moviesAdapter

        rcvNewMovies.setHasFixedSize(true)
        rcvNewMovies.adapter = moviesAdapter

        rcvAllMovies.setHasFixedSize(true)
        rcvAllMovies.adapter = moviesAdapter

        imbPopularMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbNewMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }
        imbAllMovies.setOnClickListener { startActivity(Intent(requireActivity(), EntireListActivity::class.java)) }

    }

    /*fun observeMovieInfo(movieInfoId: Int): MovieInfoModel? {
        var movieInfoModel: MovieInfoModel? = null
        CoroutineScope(Dispatchers.Main).launch {
            async {
                homeViewModel.getMovieInfoLiveData(movieInfoId).observe(viewLifecycleOwner, Observer {
                    movieInfoModel = it as MovieInfoModel
                })
            }.await()
        }
            return movieInfoModel
        }

        fun observeContent(contentId: Int): ContentModel? {
            var contentModel: ContentModel? = null

            homeViewModel.getContentLiveData(contentId).observe(viewLifecycleOwner, Observer {
                contentModel = it as ContentModel
            })
            return contentModel
        }*/


    }