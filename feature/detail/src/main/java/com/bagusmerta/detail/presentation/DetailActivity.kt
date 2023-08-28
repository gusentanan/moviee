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
package com.bagusmerta.feature.detail.presentation


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.core.utils.DataMapper
import com.bagusmerta.core.utils.DataMapper.mapMovieDetailToMoviee
import com.bagusmerta.detail.presentation.FullscreenYouTubePlayerActivity
import com.bagusmerta.feature.detail.R
import com.bagusmerta.feature.detail.databinding.ActivityDetailBinding
import com.bagusmerta.feature.detail.presentation.adapter.CastAdapter
import com.bagusmerta.feature.detail.presentation.adapter.SimilarMovieAdapter
import com.bagusmerta.utility.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val detailViewModel: DetailViewModel by viewModel()
    private val castAdapter: CastAdapter by lazy { CastAdapter(this) }
    private val similarMovieAdapter: SimilarMovieAdapter by lazy { SimilarMovieAdapter(this) }

    private var itemCast = mutableListOf<Cast>()
    private var itemSimilarMovie = mutableListOf<Moviee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)
        handleBackPressed()
        initView()
        initStatusBar()

        initRecyclerView()
        initStateObserver()
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT in 21..29) {
            window.statusBarColor = Color.TRANSPARENT
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        } else if (Build.VERSION.SDK_INT >= 30) {
            window.statusBarColor = Color.TRANSPARENT
            // Making status bar overlaps with the activity
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    private fun handleBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(1000) {
                onBackPressedDispatcher.onBackPressed() }
        }
        binding.itemTopContainer.btnBackDetail.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
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

            val genreString = DataMapper.mappingMovieGenreListFromId(data.genres)
                .joinToString(" • ") { it.name.toString() }

            itemTopContainer.apply {
                ivPoster.loadCoilImage(data.posterPath)
                backdropImage.loadCoilImageHQ(data.backdropPath)
                tvTitleDetail.text = data.title
                tvGenreInfo.text = genreString
                tvRatingDetail.text = data.rating?.toPercentageString()
                tvRatingCount.text = data.voteCount?.toKFormatString()
            }

            itemInfoContainer.apply {
                vOriginalTitle.text = data.originalTitle
                vOriginalLanguage.text = data.originalLanguage
                vStatus.text = data.status
                vRuntime.text = getString(R.string.runtime_movie_detail, data.runtime?.div(60), data.runtime?.rem(60))
                vOriginalLanguage.text = getOriginalLanguage(data.originalLanguage)
                vProductionCountries.text = data.productionCountries
                vBudget.text = getCurrency(data.budget)
                vRevenue.text = getCurrency(data.revenue)
                vReleaseDate.text = formatMediaDateMonth(data.releaseDate)
                vProductionCompanies.text = data.productionCompanies?.joinToString("\n")
            }

            tvTagline.text = data.tagline
            tvOverview.text = data.overview

            itemTrailerContainer.apply {
                ivTrailerThumbnail.loadCoilImageHQ(data.backdropPath)
                tvTrailerTitle.text = data.titleVideo
                btnWatchTrailer.setOnClickListener {
                    this@DetailActivity.startActivity(Intent(this@DetailActivity, FullscreenYouTubePlayerActivity::class.java).apply {
                            putExtra(FullscreenYouTubePlayerActivity.KEY_TRAILERS, data.keyVideo)
                        })
                }
            }

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

    private fun getOriginalLanguage(data: String?): String {
        return (LanguageEnum.values().find { it.isoCode == data } ?: LanguageEnum.UNKNOWN).toString()
    }
    private fun getCurrency(data: Double?): String{
        return NumberFormat
            .getCurrencyInstance(Locale.US)
            .format(data)
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


    companion object{
        const val MOVIEE = "moviee"
    }
}
