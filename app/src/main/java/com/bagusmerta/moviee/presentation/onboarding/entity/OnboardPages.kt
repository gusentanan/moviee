package com.bagusmerta.moviee.presentation.onboarding.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bagusmerta.moviee.R

// enum class to represent each onboarding pages
enum class OnboardPages (
    @StringRes val subTitleRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val logoRes: Int
    ) {

        FIRST_PAGE(R.string.tv_onboard_subtitle1,R.string.tv_onboard_description1, R.drawable.img_onboard_first),
        SECOND_PAGE(R.string.tv_onboard_subtitle2,R.string.tv_onboard_description2, R.drawable.img_onboard_second),
        THIRD_PAGE(R.string.tv_onboard_subtitle3,R.string.tv_onboard_description3, R.drawable.img_onboard_third)

    }