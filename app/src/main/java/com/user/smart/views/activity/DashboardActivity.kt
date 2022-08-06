package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.user.smart.R
import com.user.smart.databinding.ActivityDashboardBinding
import com.user.smart.utils.AppUtils

class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDashboardBinding
    var isProfileMenuVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.imageViewSelectStore.setOnClickListener(this)
        binding.toolbar.imageViewProfile.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when (view) {
            binding.toolbar.imageViewSelectStore -> {
                val intent = Intent(this@DashboardActivity, SelectStoreActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            binding.toolbar.imageViewProfile -> {
                isProfileMenuVisible = if (isProfileMenuVisible) {
                    AppUtils.hideProfileOptionsView(binding.profileMenuLayout)
                    binding.toolbar.profileImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_person_24
                        )
                    )
                    false
                } else {
                    AppUtils.showProfileOptionsView(binding.profileMenuLayout)
                    binding.toolbar.profileImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_close_24
                        )
                    )
                    true
                }
            }
        }
    }
}