package com.ptkako.nv.novusvision.adapter

import adapterViewBinding
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.ListItemNumberBinding

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
        Log.d("adaptPos", "number: $number")
        Log.d("adaptPos", "positon: $position")
        binding.txtSeasonNumber.text = number
        binding.txtSeasonNumber.setOnClickListener {
            Log.d("adaptPos", "clickListener: $it")
            Log.d("adaptPos", "clickListener: $position")
           // it.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            binding.txtSeasonNumber.setTextColor(context.resources.getColor(R.color.colorPrimary))
        }
    }

}