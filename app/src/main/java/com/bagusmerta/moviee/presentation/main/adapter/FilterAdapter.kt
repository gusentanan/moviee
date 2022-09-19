package com.bagusmerta.moviee.presentation.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ItemFilterComponentBinding


class FilterAdapter(private val context: Context): RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private var filterItems = mutableListOf<FilterData>()

    inner class ViewHolder(private val binding: ItemFilterComponentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterData){
            binding.apply {
                if(item.isSelected){
                    tvTitleFilter.apply {
                        isSelected = true
                        text = item.filter
                    }
                }else {
                    tvTitleFilter.apply {
                        isSelected = false
                        text = item.filter
                    }
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val binding = ItemFilterComponentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        holder.bind(filterItems[position])
    }

    override fun getItemCount(): Int = filterItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: MutableList<FilterData>){
        this.filterItems = data
        notifyDataSetChanged()
    }
}

data class FilterData(
    val filter: String,
    val isSelected: Boolean = false
)