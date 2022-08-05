package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.user.smart.R
import com.user.smart.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.imageViewSelectStore.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.toolbar.imageViewSelectStore -> {
                val intent = Intent(this@DashboardActivity, SelectStoreActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            binding.toolbar.imageViewProfile -> {
            }
        }
    }
}