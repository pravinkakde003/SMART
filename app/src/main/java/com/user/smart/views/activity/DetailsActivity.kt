package com.user.smart.views.activity

import android.os.Bundle
import com.user.smart.R
import com.user.smart.databinding.ActivityDetailsBinding
import com.user.smart.utils.AppConstant
import com.user.smart.views.fragments.POSClosingSalesFragment
import com.user.smart.views.fragments.POSLiveFragment

class DetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.getIntExtra(AppConstant.SELECTED_DASHBOARD_ITEM_KEY, 1)

        when (data) {
            1 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, POSLiveFragment())
                    .commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, POSClosingSalesFragment())
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}