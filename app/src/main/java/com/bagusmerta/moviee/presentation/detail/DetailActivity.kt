package com.bagusmerta.moviee.presentation.detail


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityDetailBinding
import com.bagusmerta.utility.hideStatusBar
import com.bagusmerta.utility.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()

        initStateObserver()
    }

    private fun initView() {
        binding.btnBack.setOnClickListener { onBackPressed() }
        hideStatusBar()
    }

    private fun initStateObserver() {
        val moviee = intent.getParcelableExtra<Moviee>(MOVIEE)
//        moviee?.let { setDetailView(it) }

        with(detailViewModel){
//            moviee?.id?.let { checkFavoriteMovies(it) }
            btnState.observe(this@DetailActivity){
                it?.let { handleButtonSaveIcon(it) }
            }
            result.observe(this@DetailActivity){
                setDetailView(it)
            }
        }
    }

    private fun setDetailView(data: MovieeDetail) {
        binding.apply {
            ivDetail.loadImage(data.posterPath)
            tvTitle.text = data.title
            tvReleaseDate.text = data.releaseDate
            tvOverviewDetail.text = data.overview
            tvRating.text = data.rating.toString()

//            var favoriteState = data.isFavorite!!
//            btnSave.setOnClickListener {
//                favoriteState = !favoriteState
//                detailViewModel.setFavoriteMovies(data, favoriteState)
//            }
        }
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