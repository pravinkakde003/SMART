package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.user.smart.R
import com.user.smart.databinding.ActivityOnboardingBinding
import com.user.smart.models.OnBoardingModel
import com.user.smart.views.fragments.OnBoardingPageFragment

class OnBoardingActivity : AppCompatActivity() {
    lateinit var onBoardingPages: ArrayList<OnBoardingModel>
    lateinit var slideInAnimRegister: Animation
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        populateOnBoardingData()
        updateViews()
        slideInAnimRegister =
            AnimationUtils.loadAnimation(this, R.anim.onboarding_button_slide_in_from_right)
    }

    private fun populateOnBoardingData() {
        val titles = resources.getStringArray(R.array.onBoardingTitles)
        val descriptions = resources.getStringArray(R.array.onBoardingDescription)
        val imageResIds = resources.obtainTypedArray(R.array.onBoardingImages)
        onBoardingPages = ArrayList()
        for (i in titles.indices) {
            onBoardingPages.add(
                OnBoardingModel(
                    titles[i],
                    descriptions[i],
                    imageResIds.getResourceId(i, 0)
                )
            )
        }
        imageResIds.recycle()
    }

    private fun updateViews() {
        binding.viewPager.adapter = OnBoardingPagerAdapter(supportFragmentManager)
        binding.indicator.attachViewPager(binding.viewPager)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

            }
        })

        binding.finishButton.setOnClickListener {
            startActivity(Intent(this@OnBoardingActivity, LoginSignupInfoActivity::class.java))
            finish()
        }
    }


    private inner class OnBoardingPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int = onBoardingPages.size
        override fun getItem(position: Int): Fragment =
            OnBoardingPageFragment.newInstance(onBoardingPages[position])
    }
}