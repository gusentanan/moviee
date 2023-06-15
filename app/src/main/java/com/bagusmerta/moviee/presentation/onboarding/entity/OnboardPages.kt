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
