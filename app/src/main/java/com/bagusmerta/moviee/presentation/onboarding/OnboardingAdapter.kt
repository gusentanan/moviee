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