package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.ptkako.nv.novusvision.databinding.ListItemEpisodeBinding
import com.ptkako.nv.novusvision.model.EpisodeModel
import com.ptkako.nv.novusvision.ui.activity.VideoStreamingActivity
import com.ptkako.nv.novusvision.utility.getDate
import com.ptkako.nv.novusvision.utility.getDateString
import com.ptkako.nv.novusvision.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeAdapter(private val context: Context, val movieName: String, val movieId: String,
                     val firebaseAuth: FirebaseAuth, val movieDetailViewModel: MovieDetailViewModel) : ListAdapter<EpisodeModel, EpisodeAdapter.EpisodeViewHolder>(diffCallback) {

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
                CoroutineScope(Dispatchers.Main).launch {
                    val history = hashMapOf("movie_id" to movieId, "user_id" to firebaseAuth.currentUser!!.uid,
                        "last_played_time" to "00:00", "finish" to "0", "last_watch" to getDateString(getDate(),
                            "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "dd-MMM-yyyy hh:mm:ss aaa"))
                    val existing = movieDetailViewModel.checkExistingHistory(movieId, firebaseAuth.currentUser!!.uid,
                        "00:00", "0")
                    if (existing == null) {
                        movieDetailViewModel.addHistory(history)
                    }

                    val episodeList = ArrayList<String>()
                    currentList.forEach()
                    {
                        episodeList.add(it.episode_url)
                    }
                    val titleList = ArrayList<String>()
                    currentList.forEach()
                    {
                        titleList.add("$movieName ${it.episode_id}")
                    }
                    val i = Intent(context, VideoStreamingActivity::class.java)
                    i.putExtra("videopath", episode.episode_url)
                    i.putExtra("title", "$movieName ${episode.episode_id}")
                    i.putExtra("episodelist", episodeList)
                    i.putExtra("titlelist", titleList)
                    context.startActivity(i)
                }
            }
            Glide.with(context).load(episode.episode_photo).into(binding.imgEpisodePhoto)
            binding.txtEpisodeNumber.text = episode.episode_id
            binding.txtTitle.text = episode.episode_title
            binding.txtEpisodeDescription.text = episode.episode_description
        }
    }

}