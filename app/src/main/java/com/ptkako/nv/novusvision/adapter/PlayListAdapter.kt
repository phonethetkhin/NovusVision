package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemEpisodeBinding
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.ui.activity.MovieDetailActivity
import com.ptkako.nv.novusvision.ui.activity.SeriesDetailActivity

class PlayListAdapter(private val context: Context) : ListAdapter<MovieModel, PlayListAdapter.PlayListViewHolder>(diffCallback) {

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

    inner class PlayListViewHolder(val binding: ListItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val v = parent.adapterViewBinding(ListItemEpisodeBinding::inflate)
        return PlayListViewHolder(v)
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        val playListItem = getItem(position)
        with(holder)
        {
            binding.cslEpisode.setOnClickListener {
                if (playListItem.series_id == null) {
                    val i = Intent(context, MovieDetailActivity::class.java)
                    val b = Bundle()
                    b.putParcelable("movie", playListItem)
                    i.putExtras(b)
                    context.startActivity(i)
                } else {
                    val i = Intent(context, SeriesDetailActivity::class.java)
                    val b = Bundle()
                    b.putParcelable("series", playListItem)
                    i.putExtras(b)
                    context.startActivity(i)
                }
            }
            binding.txtEpisodeNumber.visibility = View.GONE
            Glide.with(context).load(playListItem.movie_photo).into(binding.imgEpisodePhoto)
            binding.txtTitle.text = playListItem.movie_name
            binding.txtEpisodeDescription.text = playListItem.overview
        }
    }

}