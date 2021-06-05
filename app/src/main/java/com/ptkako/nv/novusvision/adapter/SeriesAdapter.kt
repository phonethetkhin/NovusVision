package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ListItemMoviesBinding
import com.ptkako.nv.novusvision.model.SeriesModel
import com.ptkako.nv.novusvision.ui.activity.SeriesDetailActivity

class SeriesAdapter(private val context: Context) : ListAdapter<SeriesModel, SeriesAdapter.SeriesViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<SeriesModel>() {
            override fun areItemsTheSame(oldItem: SeriesModel, newItem: SeriesModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SeriesModel, newItem: SeriesModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class SeriesViewHolder(val binding: ListItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val v = parent.adapterViewBinding(ListItemMoviesBinding::inflate)
        return SeriesViewHolder(v)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = getItem(position)
        setData(series, holder)
    }

    private fun setData(series: SeriesModel, holder: SeriesViewHolder) {
        with(holder)
        {
            Glide.with(context).load(series.movie_photo).into(binding.imgMoviesImage)
            binding.imgMoviesImage.setOnClickListener {
                val i = Intent(context, SeriesDetailActivity::class.java)
                val b = Bundle()
                b.putParcelable("series", series)
                i.putExtras(b)
                context.startActivity(i)
            }
            binding.txtMoviesTitle.text = series.movie_name
        }
    }
}