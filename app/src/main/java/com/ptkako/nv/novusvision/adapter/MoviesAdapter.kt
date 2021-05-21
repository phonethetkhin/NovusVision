package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemMoviesBinding
import com.ptkako.nv.novusvision.model.ContentModel
import com.ptkako.nv.novusvision.model.MovieInfoModel
import com.ptkako.nv.novusvision.model.MoviesModel
import com.ptkako.nv.novusvision.ui.activity.MovieDetailActivity
import com.ptkako.nv.novusvision.utility.showToast
import com.ptkako.nv.novusvision.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware

class MoviesAdapter(private val app: FragmentActivity, override val di: DI, private val homeViewModel: HomeViewModel) : ListAdapter<MoviesModel, MoviesAdapter.MoviesViewHolder>(diffCallback), DIAware {
    private lateinit var binding: ListItemMoviesBinding
    lateinit var movieInfoModel: MovieInfoModel
    lateinit var contentModel: ContentModel

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MoviesModel>() {
            override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class MoviesViewHolder(view: View) :
        RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        binding = parent.adapterViewBinding(ListItemMoviesBinding::inflate)
        return MoviesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movies = getItem(position)
        CoroutineScope(Dispatchers.Main).launch {
            getMovieInfo(movies.movie_info_id)
        }
    }

    private suspend fun getMovieInfo(movieInfoId: Int) {
        homeViewModel.getMovieInfo(movieInfoId)
        homeViewModel.movieInfoLiveData.observe(app, Observer {
            when (it) {
                is Exception -> {
                    app.showToast(it.localizedMessage)
                }
                else -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        movieInfoModel = it as MovieInfoModel
                        getContent()
                    }
                }
            }
        })
    }

    private suspend fun getContent() {
        homeViewModel.getContent(movieInfoModel.content_id)
        homeViewModel.contentLiveData.observe(app, Observer {
            when (it) {
                is Exception -> {
                    app.showToast(it.localizedMessage)
                }
                else -> {
                    contentModel = it as ContentModel
                    setData()
                }

            }
        })
    }

    private fun setData() {
        Glide.with(app).load(contentModel.image_path).into(binding.imgMoviesImage)
        binding.imgMoviesImage.setOnClickListener {
            app.startActivity(Intent(app, MovieDetailActivity::class.java))
        }
        binding.txtMoviesTitle.text = movieInfoModel.movie_name
    }
}