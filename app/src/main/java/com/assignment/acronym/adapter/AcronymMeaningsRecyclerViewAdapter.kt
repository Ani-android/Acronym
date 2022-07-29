package com.assignment.acronym.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.acronym.databinding.RecyclerViewMeningItemLayoutBinding

class AcronymMeaningsRecyclerViewAdapter :
    RecyclerView.Adapter<AcronymMeaningsRecyclerViewAdapter.MeaningViewHolder>() {

    private var meaningItems = mutableListOf<String>()

    inner class MeaningViewHolder(val binding: RecyclerViewMeningItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            RecyclerViewMeningItemLayoutBinding.inflate(inflater, parent, false)
        return MeaningViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.binding.tvMeaning.text = meaningItems[position]
    }

    override fun getItemCount() = meaningItems.size

    fun setData(meaningItems: List<String>) {
        this.meaningItems.clear()
        this.meaningItems.addAll(meaningItems)
        notifyDataSetChanged()
    }
}