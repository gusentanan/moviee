/*
 * Designed and developed by 2023 gusentanan (Bagus Merta)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bagusmerta.feature.detail.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.feature.detail.databinding.ItemCastBinding
import com.bagusmerta.utility.loadCoilImage

class CastAdapter(private val context: Context): RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    private var itemCast = mutableListOf<Cast>()

    inner class ViewHolder(private val binding: ItemCastBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Cast){
            binding.apply {
                crewCharacterName.text = item.character
                crewRealName.text = item.name
                crewPicture.loadCoilImage(item.profilePicPath)
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
