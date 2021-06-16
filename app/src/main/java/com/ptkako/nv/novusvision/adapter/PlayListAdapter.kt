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
import com.ptkako.nv.novusvision.model.CombinedModel
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.model.SeriesModel
import com.ptkako.nv.novusvision.ui.activity.MovieDetailActivity
import com.ptkako.nv.novusvision.ui.activity.SeriesDetailActivity

class PlayListAdapter(private val context: Context) : ListAdapter<CombinedModel, PlayListAdapter.PlayListViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CombinedModel>() {
            override fun areItemsTheSame(oldItem: CombinedModel, newItem: CombinedModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CombinedModel, newItem: CombinedModel): Boolean {
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
                    val movieModel = MovieModel(playListItem.casts, playListItem.content_rating,
                        playListItem.country, playListItem.duration, playListItem.full_video_path!!,
                        playListItem.genres, playListItem.is_series, playListItem.language,
                        playListItem.movie_cover_photo, playListItem.movie_name, playListItem.movie_photo,
                        playListItem.overview, playListItem.popularity, playListItem.production_year,
                        playListItem.subtitle, playListItem.status_code, playListItem.trailer_video_path)

                    b.putParcelable("movie", movieModel)
                    i.putExtras(b)
                    context.startActivity(i)
                } else {
                    val i = Intent(context, SeriesDetailActivity::class.java)
                    val b = Bundle()
                    val seriesModel = SeriesModel(playListItem.casts, playListItem.content_rating,
                        playListItem.country, playListItem.duration, playListItem.genres,
                        playListItem.is_series, playListItem.language, playListItem.movie_cover_photo,
                        playListItem.movie_name, playListItem.movie_photo, playListItem.overview,
                        playListItem.popularity, playListItem.production_year,
                        playListItem.series_id, playListItem.status_code, playListItem.trailer_video_path)
                    b.putParcelable("series", seriesModel)
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