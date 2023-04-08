package com.bagusmerta.moviee.presentation.detail


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.core.utils.DataMapper.mapMovieDetailToMoviee
import com.bagusmerta.core.utils.testHelper.mapResponseToDomain
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityDetailBinding
import com.bagusmerta.moviee.helpers.Helpers
import com.bagusmerta.moviee.presentation.detail.adapter.CastAdapter
import com.bagusmerta.moviee.presentation.detail.adapter.SimilarMovieAdapter
import com.bagusmerta.utility.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import www.sanju.motiontoast.MotionToast
import java.text.SimpleDateFormat
import java.util.Locale

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
        val moviee = intent.getParcelableExtra<Moviee>(MOVIEE)

        with(detailViewModel){
            moviee?.id?.let { getDetailMovies(it) }
            moviee?.id?.let { getMovieCast(it) }
            moviee?.id?.let { getSimilarMovie(it) }

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
                it?.let { flag -> handleLoadingState(flag) }
            }

        }
    }

    private fun handleLoadingState(flag: Boolean){
        if (!flag){
            binding.apply {
                detailLoadingShimmer.activityDetailLoader.makeGone()
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

            thumbnailContainer.backdropImage.loadImage(data.backdropPath)
            tvTitle.text = data.title
            tvMovieRating.text = String.format("%.1f", data.rating)
            tvMovieYear.text = formatMediaDate(data.releaseDate)
            tvMovieRuntime.text = getString(R.string.runtime_movie_detail, data.runtime?.div(60), data.runtime?.rem(60))
            tvOverview.text = data.overview
            tvGenres.text = Helpers.mappingMovieGenreListFromId(data.genres)
                .joinToString(" • ") { it.name.toString() }

            var favoriteState = data.isFavorite!!
            Log.d("DETL", favoriteState.toString())
            detailViewModel.apply {
                data.id?.let { checkFavoriteMovies(it) }
                btnState.observe(this@DetailActivity){
                    it?.let {
                        handleButtonSaveIcon(it)
                        favoriteState = it
                        Log.d("DETL2", favoriteState.toString())
                    }
                }
            }

            btnFavorite.setOnClickListener {
                favoriteState = !favoriteState
                val nData = mapMovieDetailToMoviee(data)
                if(favoriteState){
                    detailViewModel.setFavoriteMovies(nData, favoriteState)
                }else {
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
                btnFavorite.text = "Remove from favorite"
            }else {
                btnFavorite.text = "Add to Favorite"
            }
        }
    }

    private fun handleButtonWatch(){
        makeErrorToast("This feature is currently unavailable")
    }

    private fun formatMediaDate(date: String?): String? {
        if(!date.isNullOrEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return sdf.parse(date)
                ?.let { SimpleDateFormat("yyyy", Locale.getDefault()).format(it) }
        } else {
            return "Unknown"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        youtubePlayerListener?.let { binding.ytPlayerView.release() }

    }

    companion object{
        const val MOVIEE = "moviee"
    }
}