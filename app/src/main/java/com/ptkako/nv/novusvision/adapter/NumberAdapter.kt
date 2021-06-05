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

class NumberAdapter(val context: Context) : ListAdapter<String, NumberAdapter.NumberViewHolder>(diffCallback) {
    var rowIndex = 0

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
            binding.txtSeasonNumber.setOnClickListener {
                rowIndex = position
                notifyDataSetChanged()
                Log.d("adaptPos", "clickListener: $rowIndex")
                Log.d("adaptPos", "clickListener: $position")
            }
            val number = getItem(position)
            Log.d("adaptPos", "number: $number")
            Log.d("adaptPos", "positon: $position")
            Log.d("adaptPos", "rowIndex: $rowIndex")
            binding.txtSeasonNumber.text = number
            if (rowIndex == position) {
                Log.d("adaptPos", " hello row index same")
                binding.txtSeasonNumber.setBackgroundColor(context.resources.getColor(R.color.colorPrimary))
            } else {
                binding.txtSeasonNumber.setBackgroundColor(0)
            }
        }

    }

}