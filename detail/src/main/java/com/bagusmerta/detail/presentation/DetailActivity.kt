package com.bagusmerta.detail.presentation


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.core.utils.DataMapper.mapMovieDetailToMoviee
import com.bagusmerta.detail.R
import com.bagusmerta.detail.databinding.ActivityDetailBinding
import com.bagusmerta.detail.helpers.HelpersDetail
import com.bagusmerta.detail.presentation.adapter.CastAdapter
import com.bagusmerta.detail.presentation.adapter.SimilarMovieAdapter
import com.bagusmerta.utility.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val detailViewModel: DetailViewModel by viewModel()
    private val castAdapter: CastAdapter by lazy { CastAdapter(this) }
    private val similarMovieAdapter: SimilarMovieAdapter by lazy { SimilarMovieAdapter(this) }

    private var _youtubePlayer: YouTubePlayer ? = null
    private var youtubePlayerListener: AbstractYouTubePlayerListener ? = null
    private var itemCast = mutableListOf<Cast>()
    private var itemSimilarMovie = mutableListOf<Moviee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(binding.root)
        initView()

        initRecyclerView()
        initStateObserver()
    }

    private fun initView() {
        hideStatusBar()
    }

    private fun initStateObserver() {
        val movieeId = intent.getIntExtra(MOVIEE, 0)

        with(detailViewModel){
            getDetailMovies(movieeId)
            getMovieCast(movieeId)
            getSimilarMovie(movieeId)

            result.observe(this@DetailActivity){
                it?.let { detailMovies -> setDetailView(detailMovies) }
            }
            castResult.observe(this@DetailActivity){
                it?.let { cast -> handleCastResult(cast) }
            }
            similarMovieResult.observe(this@DetailActivity){
                it?.let { movieList -> handleSimilarMovieResult(movieList) }
            }
            loadingState.observe(this@DetailActivity){
                handleLoadingState(it)
            }
            errorState.observe(this@DetailActivity){
                handleErrorState(it)
            }
            detailedEmptyState.observe(this@DetailActivity){
                handleEmptyState(it)
            }

        }
    }

    private fun handleSimilarMovieResult(data: List<Moviee>?){
        itemSimilarMovie.clear()
        data?.let { itemSimilarMovie.addAll(it) }
        similarMovieAdapter.setSimilarMovieItems(itemSimilarMovie)
    }

    private fun handleCastResult(data: List<Cast>?){
        itemCast.clear()
        data?.let { itemCast.addAll(it) }
        castAdapter.setItemCast(itemCast)
    }

    @SuppressLint("StringFormatMatches")
    private fun setDetailView(data: MovieeDetail) {
        binding.apply {
            // init yt video player
            if(_youtubePlayer == null) {
                data.keyVideo?.let { initYouTubePlayer(it) }
            } else {
                data.keyVideo?.let { _youtubePlayer!!.cueVideo(it, 0f) }
            }

            thumbnailContainer.backdropImage.loadHighQualityImage(data.backdropPath)
            tvTitle.text = data.title
            tvMovieRating.text = String.format("%.1f", data.rating)
            tvMovieYear.text = HelpersDetail.formatMediaDate(data.releaseDate)
            tvMovieRuntime.text = getString(R.string.runtime_movie_detail, data.runtime?.div(60), data.runtime?.rem(60))
            tvOverview.text = data.overview

            val genreString =  HelpersDetail.mappingMovieGenreListFromId(data.genres)
                .joinToString(" • ") { it.name.toString() }
            tvGenres.text = genreString


            var favoriteState = data.isFavorite!!
            detailViewModel.apply {
                data.id?.let { checkFavoriteMovies(it) }
                btnState.observe(this@DetailActivity){
                    it?.let {
                        handleButtonSaveIcon(it)
                        favoriteState = it
                    }
                }
            }

            btnFavorite.setOnClickListener {
                favoriteState = !favoriteState
                val nData = mapMovieDetailToMoviee(data)
                if(favoriteState){
                    makeSuccessToast("The movie is successfully added to your favorite list")
                    detailViewModel.setFavoriteMovies(nData, favoriteState, genreString)
                }else {
                    makeSuccessToast("The movie is successfully removed from your favorite list")
                    detailViewModel.deleteFavoriteMovies(nData.id!!)
                }
            }

            btnGoWatch.setOnClickListener { handleButtonWatch() }
        }
    }


    private fun initRecyclerView(){
        with(binding){
            rvCast.layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            rvCast.setHasFixedSize(true)
            rvCast.adapter = castAdapter

            rvMovieSimilar.layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            rvMovieSimilar.setHasFixedSize(true)
            rvMovieSimilar.adapter = similarMovieAdapter
        }
    }
    private fun initYouTubePlayer(keyVideo: String) = binding.apply {
        youtubePlayerListener = object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                keyVideo.let {
                    thumbnailContainer.container.makeGone()
                    ytPlayerView.makeVisible()
                    _youtubePlayer = youTubePlayer
                    _youtubePlayer!!.cueVideo(it, 0f)
                }
            }
        }

        ytPlayerView.addYouTubePlayerListener(youtubePlayerListener!!)
    }

    private fun handleButtonSaveIcon(isFavorite: Boolean){
        binding.apply {
            if(isFavorite){
                btnFavorite.text = getString(R.string.remove_from_favorite)
            }else {
                btnFavorite.text = getString(R.string.add_to_favorite)
            }
        }
    }

    private fun handleEmptyState(state: DetailEmptyState) {
        when(state){
            is DetailEmptyState.SimilarMoviesEmptyState -> {
                binding.tvSimilarEmptyState.makeVisible()
            }
            is DetailEmptyState.CastEmptyState -> {
                binding.tvCastEmptyState.makeVisible()
            }
        }
    }

    private fun handleErrorState(msg: String?) {
        Timber.e(msg)
        this.makeErrorToast("Oops Something wrong happen!")
    }

    private fun handleLoadingState(flag: Boolean){
        if (!flag){
            binding.apply {
                detailLoadingShimmer.activityDetailLoader.makeGone()
            }
        }
    }

    private fun handleButtonWatch(){
        makeInfoToast("This feature is currently unavailable")
    }

    override fun onDestroy() {
        super.onDestroy()
        youtubePlayerListener?.let { binding.ytPlayerView.release() }
    }

    companion object{
        const val MOVIEE = "moviee"
    }
}