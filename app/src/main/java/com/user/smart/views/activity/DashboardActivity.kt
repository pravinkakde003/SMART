package com.user.smart.views.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.user.smart.R
import com.user.smart.api.ApiService
import com.user.smart.databinding.ActivityDashboardBinding
import com.user.smart.utils.AppUtils
import com.user.smart.views.adapters.DashboardMenuListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class DashboardActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDashboardBinding
    var isProfileMenuVisible = false



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

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val value = result.data?.getStringExtra("STORE_NAME")
            binding.txtStoreName.text = value
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.toolbar.imageViewSelectStore -> {
                val intent = Intent(this@DashboardActivity, SearchStoreListingActivity::class.java)
                resultLauncher.launch(intent)
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