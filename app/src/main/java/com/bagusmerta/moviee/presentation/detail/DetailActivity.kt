package com.bagusmerta.moviee.presentation.detail


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityDetailBinding
import com.bagusmerta.moviee.utils.loadImage
import com.bagusmerta.moviee.utils.makeGone
import com.bagusmerta.moviee.utils.makeToast
import com.bagusmerta.moviee.utils.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val detailViewModel: DetailViewModel by viewModel()

    private var favoriteState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initStateObserver()
    }

    private fun initStateObserver() {
        val movieId = intent.getSerializableExtra(EXTRA_MOVIEE) as Int
        detailViewModel.apply {
            btnState.observe(this@DetailActivity){
                handleButtonSaveIcon(it)
            }
            getDetailMovies(movieId).observe(this@DetailActivity) { movie ->
                when (movie) {
                    is Resource.Success -> {
                        binding.progressBar.makeGone()
                        movie.data?.let { it ->
                            setDetailView(it)
                            favoriteState = it.isFavorite == true
                        }
                        handleButtonSaveIcon(favoriteState)
                    }
                    is Resource.Error -> {
                        binding.progressBar.makeGone()
                        handleErrorState()
                    }
                    is Resource.Loading -> binding.progressBar.makeVisible()
                }
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
                favoriteState = !favoriteState
                detailViewModel.setFavoriteMovies(data, favoriteState)
            }
        }
    }

    private fun handleErrorState(){
        this@DetailActivity.makeToast(getString(R.string.error_something_wrong))
    }

    private fun handleButtonSaveIcon(isFavorite: Boolean){
        binding.apply {
            if(isFavorite){
                btnSave.setImageResource(R.drawable.ic_baseline_bookmark_24)
            }else {
                btnSave.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
            }
        }
    }

    companion object{
        const val EXTRA_MOVIEE = "extra_moviee"
    }
}