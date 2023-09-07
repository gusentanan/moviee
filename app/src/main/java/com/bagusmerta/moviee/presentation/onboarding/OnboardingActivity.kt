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
package com.bagusmerta.moviee.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bagusmerta.moviee.databinding.ActivityOnboardingBinding
import com.bagusmerta.moviee.helpers.OnboardPrefManager
import com.bagusmerta.moviee.presentation.main.MainActivity
import com.bagusmerta.moviee.presentation.onboarding.entity.OnboardPages
import com.bagusmerta.utility.extensions.initStatusBar

class OnboardingActivity : AppCompatActivity() {

    private val binding: ActivityOnboardingBinding by lazy { ActivityOnboardingBinding.inflate(layoutInflater) }
    private val numOfPages by lazy { OnboardPages.values().size }
    private val onboardingAdapter: OnboardingAdapter by lazy { OnboardingAdapter(this) }
    private val prefManager: OnboardPrefManager by lazy { OnboardPrefManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initStatusBar()

        if(!prefManager.isFirstTime){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        initSlider()
        handleButtonListener()

    }

    private fun initSlider() {
        binding.apply {
            vpSlider.adapter = onboardingAdapter
            slideChangeListener()

            val dotIndicator = pageIndicator
            dotIndicator.attachTo(vpSlider)
        }
    }

    private fun slideChangeListener() {
        binding.apply {
            vpSlider.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if(numOfPages > 1){
                        val progress = (position + positionOffset) / (numOfPages - 1)
                        onboardingRoot.progress = progress
                    }
                }
            })
        }
    }

    private fun handleButtonListener(){
        binding.apply {
            btnNext.setOnClickListener { navigateToNextSlide(vpSlider) }
            btnSkip.setOnClickListener {
                disableFirstTime()
                startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                finish()
            }
            btnStart.setOnClickListener {
                disableFirstTime()
                startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun disableFirstTime(){
        prefManager.isFirstTime = false
    }

    private fun navigateToNextSlide(slider: ViewPager2){
        val next = slider.currentItem.plus(1)
        slider.setCurrentItem(next, true)
    }
}
