package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemMoviesBinding
import com.ptkako.nv.novusvision.model.MoviesModel
import com.ptkako.nv.novusvision.ui.activity.MovieDetailActivity

class MoviesAdapter(private val context: Context) : ListAdapter<MoviesModel, MoviesAdapter.MoviesViewHolder>(diffCallback) {
    private lateinit var binding: ListItemMoviesBinding

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MoviesModel>() {
            override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
                return oldItem.id == newItem.id
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

        Glide.with(context).load(movies.imgUrl).into(binding.imgMoviesImage)
        binding.imgMoviesImage.setOnClickListener {
            context.startActivity(Intent(context, MovieDetailActivity::class.java))
        }
        binding.txtMoviesTitle.text = movies.moviesTitle
    }

}