package com.bagusmerta.moviee.presentation.detail


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityDetailBinding
import com.bagusmerta.moviee.utils.loadImage
import com.bagusmerta.moviee.utils.makeToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.detail_page_title)

        initStateObserver()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initStateObserver() {
        val moviee = intent.getParcelableExtra<Moviee>(MOVIEE)
        moviee?.let { setDetailView(it) }

        with(detailViewModel){
            moviee?.id?.let { checkFavoriteMovies(it) }
            btnState.observe(this@DetailActivity){
                it?.let { handleButtonSaveIcon(it) }
            }
            result.observe(this@DetailActivity){
                setDetailView(it)
            }
        }
    }

    private fun setDetailView(data: Moviee) {
        binding.apply {
            ivDetail.loadImage(data.posterPath)
            tvTitle.text = data.title
            tvReleaseDate.text = data.releaseDate
            tvOverviewDetail.text = data.overview

            var favoriteState = data.isFavorite!!
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
        const val MOVIEE = "moviee"
    }
}