package com.bagusmerta.moviee.presentation.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.moviee.databinding.ActivityDetailBinding
import com.bagusmerta.moviee.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initStateObserver()
    }

    private fun initStateObserver() {
        val movie_id = intent.getSerializableExtra(EXTRA_MOVIEE) as Int
        detailViewModel.getDetailMovies(movie_id).observe(this){
            when(it){
                is Resource.Success -> it.data?.let { movie -> setDetailView(movie) }
            }
        }
    }

    private fun setDetailView(data: Moviee) {
        binding.apply {
            ivDetail.loadImage(data.posterPath)
            tvTitle.text = data.title
            tvReleaseDate.text = data.releaseDate
            tvOverviewDetail.text = data.overview

            btnSave.setOnClickListener {
                detailViewModel.setFavoriteMovies(data, false)
            }
        }
    }

    companion object{
        const val EXTRA_MOVIEE = "extra_moviee"
    }
}