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
package com.bagusmerta.moviee.presentation.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.moviee.databinding.ItemOnboardingBinding
import com.bagusmerta.moviee.presentation.onboarding.entity.OnboardPages

class OnboardingAdapter(private val context: Context): RecyclerView.Adapter<OnboardingAdapter.ViewHolder>() {

    private var items: Array<OnboardPages> = OnboardPages.values()

    inner class ViewHolder(private val binding: ItemOnboardingBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: OnboardPages){
            binding.apply {
                val res = binding.root.context.resources
                tvSubtitle.text = res.getString(item.subTitleRes)
                tvDesc.text = res.getString(item.descriptionRes)
                ivImg.setImageResource(item.logoRes)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnboardingAdapter.ViewHolder {
        val binding = ItemOnboardingBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
