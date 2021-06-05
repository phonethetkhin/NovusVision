package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemEpisodeBinding
import com.ptkako.nv.novusvision.model.EpisodeModel

class EpisodeAdapter(private val context: Context) : ListAdapter<EpisodeModel, EpisodeAdapter.EpisodeViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<EpisodeModel>() {
            override fun areItemsTheSame(oldItem: EpisodeModel, newItem: EpisodeModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EpisodeModel, newItem: EpisodeModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class EpisodeViewHolder(val binding: ListItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val v = parent.adapterViewBinding(ListItemEpisodeBinding::inflate)
        return EpisodeViewHolder(v)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)
        with(holder)
        {
            Glide.with(context).load(episode.imgUrl).into(binding.imgEpisodePhoto)
            binding.txtEpisodeNumber.text = "Episode ${episode.episodeNumber}"
            binding.txtEpisodeDescription.text = episode.episodeDescription
        }
    }

}