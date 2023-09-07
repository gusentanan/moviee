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
package com.bagusmerta.moviee.presentation.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.common_ui.databinding.ItemMainComponentBinding
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.feature.detail.presentation.DetailActivity
import com.bagusmerta.utility.extensions.loadCoilImage

class HorizontalMovieListAdapter(private val context: Context): RecyclerView.Adapter<HorizontalMovieListAdapter.ViewHolder>(){

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemMainComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivPoster.loadCoilImage(item.posterPath)
                tvMovieRating.text = item.rating.toString()

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item.id)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalMovieListAdapter.ViewHolder {
        val binding = ItemMainComponentBinding.inflate(LayoutInflater.from(context), parent , false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalMovieListAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMoviesItem(data: List<Moviee?>){
        this.items = data as MutableList<Moviee>
        notifyDataSetChanged()
    }

}
