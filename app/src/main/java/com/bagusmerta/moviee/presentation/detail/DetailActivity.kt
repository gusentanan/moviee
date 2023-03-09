package com.bagusmerta.moviee.presentation.detail


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.moviee.databinding.ActivityDetailBinding
import com.bagusmerta.moviee.helpers.Helpers
import com.bagusmerta.moviee.presentation.detail.adapter.CastAdapter
import com.bagusmerta.utility.hideStatusBar
import com.bagusmerta.utility.loadImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val detailViewModel: DetailViewModel by viewModel()
    private val castAdapter: CastAdapter by lazy { CastAdapter(this) }

    private var _youtubePlayer: YouTubePlayer ? = null
    private var youtubePlayerListener: AbstractYouTubePlayerListener ? = null
    private var itemCast = mutableListOf<Cast>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()

        initRecyclerView()
        initStateObserver()
    }

    private fun initView() {
//        binding.btnBack.setOnClickListener { onBackPressed() }
        hideStatusBar()
    }

    private fun initStateObserver() {
        val moviee = intent.getParcelableExtra<Moviee>(MOVIEE)

        with(detailViewModel){
            moviee?.id?.let { getDetailMovies(it) }
            moviee?.id?.let { getMovieCast(it) }

//            moviee?.id?.let { checkFavoriteMovies(it) }
            btnState.observe(this@DetailActivity){
                it?.let { handleButtonSaveIcon(it) }
            }
            result.observe(this@DetailActivity){
                it?.let { detailMovies -> setDetailView(detailMovies) }
            }
            castResult.observe(this@DetailActivity){
                it?.let { cast -> handleCastResult(cast) }
            }
        }
    }

    private fun handleCastResult(data: List<Cast>?){
        itemCast.clear()
        data?.let { itemCast.addAll(it) }
        castAdapter.setItemCast(itemCast)
    }

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
            tvMovieRuntime.text = "${data.runtime?.div(60)} h ${data.runtime?.rem(60)} m"
            tvOverview.text = data.overview
            tvGenres.text = Helpers.mappingMovieGenreListFromId(data.genres)
                .joinToString(" • ") { it.name.toString() }

//            var favoriteState = data.isFavorite!!
//            btnSave.setOnClickListener {
//                favoriteState = !favoriteState
//                detailViewModel.setFavoriteMovies(data, favoriteState)
//            }
        }
    }

    private fun initRecyclerView(){
        with(binding){
            rvCast.layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            rvCast.setHasFixedSize(true)
            rvCast.adapter = castAdapter
        }
    }
    private fun initYouTubePlayer(keyVideo: String) = binding.apply {
        youtubePlayerListener = object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                keyVideo.let {
                    thumbnailContainer.container.isGone = true
                    ytPlayerView.isGone = false
                    _youtubePlayer = youTubePlayer
                    _youtubePlayer!!.cueVideo(it, 0f)
                }
            }
        }
        ytPlayerView.addYouTubePlayerListener(youtubePlayerListener!!)
    }

    private fun handleButtonSaveIcon(isFavorite: Boolean){
//        binding.apply {
//            if(isFavorite){
//                btnSave.setImageResource(R.drawable.ic_baseline_bookmark_24)
//            }else {
//                btnSave.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
//            }
//        }
    }

    private fun formatMediaDate(date: String?): String? {
        if(!date.isNullOrEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return sdf.parse(date)
                ?.let { SimpleDateFormat("yyyy", Locale.getDefault()).format(it) }
        } else {
            return "Uknown"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        youtubePlayerListener?.let { binding.ytPlayerView.removeYouTubePlayerListener(it) }
    }

    companion object{
        const val MOVIEE = "moviee"
    }
}