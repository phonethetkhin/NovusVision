package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemMoviesBinding
import com.ptkako.nv.novusvision.model.ContentModel
import com.ptkako.nv.novusvision.model.MovieInfoModel
import com.ptkako.nv.novusvision.model.MoviesModel
import com.ptkako.nv.novusvision.ui.activity.MovieDetailActivity
import com.ptkako.nv.novusvision.ui.fragment.MovieFragment

class MoviesAdapter(private val fragment: Fragment) : ListAdapter<MoviesModel, MoviesAdapter.MoviesViewHolder>(diffCallback) {
    private lateinit var binding: ListItemMoviesBinding

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
        /* val movieInfo = (fragment as MovieFragment).observeMovieInfo(movies.movie_info_id)
        Log.d("data", movieInfo.toString())
        if (movieInfo != null) {
            movieInfo as MovieInfoModel
            val content = (fragment as MovieFragment).observeContent(movieInfo.content_id)
            Log.d("data", content.toString())

            if (content != null) {
                setData(movieInfo, content)
            }*/
    }

   /* private fun setData(movieInfoModel: MovieInfoModel, contentModel: ContentModel) {
        Glide.with(fragment.requireContext()).load(contentModel.image_path).into(binding.imgMoviesImage)
        binding.imgMoviesImage.setOnClickListener {
            fragment.requireContext().startActivity(Intent(fragment.requireContext(), MovieDetailActivity::class.java))
        }
        binding.txtMoviesTitle.text = movieInfoModel.movie_name
    }*/
}