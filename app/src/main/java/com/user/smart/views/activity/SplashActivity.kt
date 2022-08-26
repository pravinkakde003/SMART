package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.user.smart.R
import com.user.smart.databinding.ActivitySplashBinding
import com.user.smart.utils.AppConstant.SPLASH_SCREEN_DELAY
import com.user.smart.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNavigation()
    }

    private fun checkNavigation() {
        Handler(Looper.getMainLooper()).postDelayed({
            var intent = getNavigationIntent()
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }, SPLASH_SCREEN_DELAY.toLong())
    }

    private fun getNavigationIntent() = when {
        preferenceManager.getToken() != null -> {
            when {
                preferenceManager.getSelectedStoreObject() != null -> {
                    Intent(this, DashboardActivity::class.java)
                }
                else -> {
                    Intent(this, SelectStoreActivity::class.java)
                }
            }
        }
        else -> {
            Intent(this, OnBoardingActivity::class.java)
        }
    }
}