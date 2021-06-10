package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemEpisodeBinding
import com.ptkako.nv.novusvision.model.EpisodeModel
import com.ptkako.nv.novusvision.ui.activity.VideoStreamingActivity

class EpisodeAdapter(private val context: Context) : ListAdapter<EpisodeModel, EpisodeAdapter.EpisodeViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<EpisodeModel>() {
            override fun areItemsTheSame(oldItem: EpisodeModel, newItem: EpisodeModel): Boolean {
                return oldItem == newItem
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
            binding.imgEpisodePhoto.setOnClickListener {
                val episodeList = ArrayList<String>()
                currentList.forEach()
                {
                    episodeList.add(it.episode_url)
                }
                val titleList = ArrayList<String>()
                currentList.forEach()
                {
                    titleList.add(it.episode_id)
                }
                val i = Intent(context, VideoStreamingActivity::class.java)
                i.putExtra("videopath", episode.episode_url)
                i.putExtra("title", episode.episode_id)
                i.putExtra("episodelist", episodeList)
                i.putExtra("titlelist", titleList)
                context.startActivity(i)
            }
            Glide.with(context).load(episode.episode_photo).into(binding.imgEpisodePhoto)
            binding.txtEpisodeNumber.text = episode.episode_id
            binding.txtTitle.text = episode.episode_title
            binding.txtEpisodeDescription.text = episode.episode_description
        }
    }

}