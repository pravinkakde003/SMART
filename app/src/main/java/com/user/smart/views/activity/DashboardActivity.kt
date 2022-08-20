package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.user.smart.R
import com.user.smart.databinding.ActivityDashboardBinding
import com.user.smart.utils.AppUtils
import com.user.smart.utils.PreferenceManager
import com.user.smart.views.adapters.DashboardMenuListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDashboardBinding
    var isProfileMenuVisible = false

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.imageViewSelectStore.setOnClickListener(this)
        binding.toolbar.imageViewProfile.setOnClickListener(this)
        val dashboardMenuList =
            AppUtils.getDashboardMenuList(AppUtils.getArrayListFromJson(this, R.raw.dashboard))

        binding.recyclerviewDashboard.layoutManager = GridLayoutManager(this, 3)
        var mAdapter = DashboardMenuListAdapter(this)
        mAdapter.setLisData(dashboardMenuList)
        binding.recyclerviewDashboard.adapter = mAdapter


    }

    override fun onResume() {
        super.onResume()
        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreName.text = selectedStoreObject.store_name
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.toolbar.imageViewSelectStore -> {
                if (isInternetAvailable()) {
                    val intent =
                        Intent(this@DashboardActivity, SearchStoreListingActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                } else {
                    AppUtils.showInternetAlertDialog(this)
                }
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