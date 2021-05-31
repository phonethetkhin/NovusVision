package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.ListItemEpisodeBinding
import com.ptkako.nv.novusvision.databinding.ListItemNumberBinding
import com.ptkako.nv.novusvision.model.EpisodeModel

class NumberAdapter(val context: Context) : ListAdapter<String, NumberAdapter.NumberViewHolder>(diffCallback) {
    private lateinit var binding: ListItemNumberBinding

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

    inner class NumberViewHolder(view: View) :
        RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        binding = parent.adapterViewBinding(ListItemNumberBinding::inflate)
        return NumberViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val number = getItem(position)
        binding.txtSeasonNumber.text = number
        binding.txtSeasonNumber.setOnClickListener {
            binding.txtSeasonNumber.setTextColor(context.resources.getColor(R.color.colorPrimary))
        }
    }

}