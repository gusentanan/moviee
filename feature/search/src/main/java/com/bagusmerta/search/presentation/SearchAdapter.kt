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
package com.bagusmerta.feature.search.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.MovieeSearch
import com.bagusmerta.core.utils.DataMapper
import com.bagusmerta.feature.detail.presentation.DetailActivity
import com.bagusmerta.feature.search.databinding.ItemSearchComponentBinding
import com.bagusmerta.utility.formatMediaDateYear
import com.bagusmerta.utility.loadCoilImage

class SearchAdapter(private val context: Context): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var items = mutableListOf<MovieeSearch>()

    inner class ViewHolder(private val binding: ItemSearchComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: MovieeSearch){
            binding.apply {
                ivPoster.loadCoilImage(item.backdropPath)
                tvSearchMovieTitle.text = item.title
                tvMovieRating.text = String.format("%.1f", item.rating)
                tvMovieYear.text = formatMediaDateYear(item.releaseDate)
                val genreString =  DataMapper.mappingMovieGenreListFromId(item.genreId)
                    .joinToString(" • ") { it.name.toString() }

                tvGenres.text = genreString

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item.id)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  binding = ItemSearchComponentBinding.inflate(LayoutInflater.from(context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: MutableList<MovieeSearch>){
        this.items = data
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems(){
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }
}
