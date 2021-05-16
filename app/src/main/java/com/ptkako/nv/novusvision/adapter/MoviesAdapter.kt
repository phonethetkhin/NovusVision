package com.ptkako.nv.novusvision.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemMoviesBinding
import com.ptkako.nv.novusvision.model.MoviesModel

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
        binding = ListItemMoviesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MoviesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movies = getItem(position)

        Glide.with(context).load(movies.imgUrl).into(binding.ivMoviesImage)
        binding.tvMoviesTitle.text = movies.moviesTitle
    }

}