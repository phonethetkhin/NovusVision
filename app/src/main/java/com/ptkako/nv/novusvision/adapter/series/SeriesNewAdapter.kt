package com.ptkako.nv.novusvision.adapter.series

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
import com.ptkako.nv.novusvision.databinding.ListItemMoviesBinding
import com.ptkako.nv.novusvision.model.SeriesModel
import com.ptkako.nv.novusvision.ui.activity.SeriesDetailActivity

class SeriesNewAdapter(private val context: Context) : ListAdapter<SeriesModel, SeriesNewAdapter.SeriesViewHolder>(diffCallback) {
    private lateinit var binding: ListItemMoviesBinding

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

    inner class SeriesViewHolder(view: View) :
        RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        binding = parent.adapterViewBinding(ListItemMoviesBinding::inflate)
        return SeriesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = getItem(position)
        setData(series)
    }

    private fun setData(series: SeriesModel) {
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