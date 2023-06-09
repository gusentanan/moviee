package com.bagusmerta.detail.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.detail.databinding.ItemCastBinding
import com.bagusmerta.utility.loadImage

class CastAdapter(private val context: Context): RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    private var itemCast = mutableListOf<Cast>()

    inner class ViewHolder(private val binding: ItemCastBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Cast){
            binding.apply {
                crewCharacterName.text = item.character
                crewRealName.text = item.name
                crewPicture.loadImage(item.profilePicPath)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastAdapter.ViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastAdapter.ViewHolder, position: Int) {
        holder.bind(itemCast[position])
    }

    override fun getItemCount(): Int = itemCast.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItemCast(data: MutableList<Cast>){
        this.itemCast = data
        notifyDataSetChanged()
    }

}