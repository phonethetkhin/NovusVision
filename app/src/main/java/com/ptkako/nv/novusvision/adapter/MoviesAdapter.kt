package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemMoviesBinding
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.ui.activity.MovieDetailActivity
import com.ptkako.nv.novusvision.ui.activity.SeriesDetailActivity

class MoviesAdapter(private val context: Context) : ListAdapter<MovieModel, MoviesAdapter.MoviesViewHolder>(diffCallback) {
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

    inner class MoviesViewHolder(val binding: ListItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val v = parent.adapterViewBinding(ListItemMoviesBinding::inflate)
        return MoviesViewHolder(v)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
            setData(movie,holder)

    }

    private fun setData(movie: MovieModel, holder: MoviesViewHolder) {
        with(holder)
        {
            Log.d("livedata", "adapter: $movie")

            Glide.with(context).load(movie.movie_photo).into(binding.imgMoviesImage)
            binding.imgMoviesImage.setOnClickListener {
                if(movie.series_id == null) {
                    val i = Intent(context, MovieDetailActivity::class.java)
                    val b = Bundle()
                    b.putParcelable("movie", movie)
                    i.putExtras(b)
                    context.startActivity(i)
                }
                else{
                    val i = Intent(context, SeriesDetailActivity::class.java)
                    val b = Bundle()
                    b.putParcelable("series", movie)
                    i.putExtras(b)
                    context.startActivity(i)
                }
            }
            binding.txtMoviesTitle.text = movie.movie_name
        }
    }
}