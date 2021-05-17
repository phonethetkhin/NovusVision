package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemEpisodeBinding
import com.ptkako.nv.novusvision.model.EpisodeModel

class EpisodeAdapter(private val context: Context) : ListAdapter<EpisodeModel, EpisodeAdapter.EpisodeViewHolder>(diffCallback) {
    private lateinit var binding: ListItemEpisodeBinding

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

    inner class EpisodeViewHolder(view: View) :
        RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        binding = parent.adapterViewBinding(ListItemEpisodeBinding::inflate)
        return EpisodeViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)

        Glide.with(context).load(episode.imgUrl).into(binding.imgEpisodePhoto)
        binding.txtEpisodeNumber.text = "Episode ${episode.episodeNumber}"
        binding.txtEpisodeDescription.text = episode.episodeDescription
    }

}