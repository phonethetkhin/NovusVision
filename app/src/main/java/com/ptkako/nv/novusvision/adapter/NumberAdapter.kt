package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.ListItemNumberBinding
import com.ptkako.nv.novusvision.ui.activity.SeriesDetailActivity
import com.ptkako.nv.novusvision.viewmodel.SeriesDetailViewModel

class NumberAdapter(val context: Context,val seriesDetailViewModel: SeriesDetailViewModel) : ListAdapter<String, NumberAdapter.NumberViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class NumberViewHolder(val binding: ListItemNumberBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val v = parent.adapterViewBinding(ListItemNumberBinding::inflate)
        return NumberViewHolder(v)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        with(holder)
        {
            val number = getItem(position)
            binding.txtSeasonNumber.setOnClickListener {
                Log.d("sid", "${position+1}")
                seriesDetailViewModel.seasonId = position+1
                (context as SeriesDetailActivity).observeEpisodeList()
                seriesDetailViewModel.rowIndex = position
                notifyDataSetChanged()
            }
            binding.txtSeasonNumber.text = number
            if (seriesDetailViewModel.rowIndex == position) {
                binding.txtSeasonNumber.setBackgroundColor(context.resources.getColor(R.color.colorPrimary))
            } else {
                binding.txtSeasonNumber.setBackgroundColor(0)
            }
        }

    }

}