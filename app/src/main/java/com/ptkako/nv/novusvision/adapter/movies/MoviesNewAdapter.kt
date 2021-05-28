package com.ptkako.nv.novusvision.adapter.movies

import adapterViewBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemMoviesBinding
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.ui.activity.MovieDetailActivity

class MoviesNewAdapter(private val context: Context) : ListAdapter<MovieModel, MoviesNewAdapter.MoviesViewHolder>(diffCallback) {
    private lateinit var binding: ListItemMoviesBinding

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
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
        val movie = getItem(position)
        setData(movie)
    }

    private fun setData(movie: MovieModel) {
        Log.d("livedata","adapter: $movie")

        Glide.with(context).load(movie.movie_photo).into(binding.imgMoviesImage)
        binding.imgMoviesImage.setOnClickListener {
            val i = Intent(context, MovieDetailActivity::class.java)
            val b = Bundle()
            b.putParcelable("movie", movie)
            i.putExtras(b)
            context.startActivity(i)
        }
        binding.txtMoviesTitle.text = movie.movie_name
    }
}