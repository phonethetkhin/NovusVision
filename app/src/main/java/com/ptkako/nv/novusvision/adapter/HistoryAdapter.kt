package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.text.Html
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemHistoryBinding
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.utility.PREF_CURRENT_TIME
import com.ptkako.nv.novusvision.utility.getStringPref
import java.util.logging.Level.parse

class HistoryAdapter(private val context: Context) : ListAdapter<MovieModel, HistoryAdapter.HistoryViewHolder>(diffCallback) {

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

    inner class HistoryViewHolder(val binding: ListItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val v = parent.adapterViewBinding(ListItemHistoryBinding::inflate)
        return HistoryViewHolder(v)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val playListItem = getItem(position)
        with(holder)
        {
            Glide.with(context).load(playListItem.movie_photo).into(binding.imgEpisodePhoto)
            binding.txtTitle.text = playListItem.movie_name
            binding.txtEpisodeDescription.text = playListItem.overview
            binding.txtLastWatch.text = ("Last Watch at : ${System.getProperty("line.separator")}${getStringPref(context, PREF_CURRENT_TIME, PREF_CURRENT_TIME, "")}")
        }
    }

}