package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.user.smart.R
import com.user.smart.databinding.ActivityDashboardBinding
import com.user.smart.utils.*
import com.user.smart.views.adapters.DashboardMenuListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDashboardBinding
    private var isProfileMenuVisible = false

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListeners()
        setMenuRecyclerView()
    }

    private fun setClickListeners() {
        binding.toolbar.toolbarParentCardView.elevation = 0f
        binding.toolbar.imageViewSelectStore.visibility = View.VISIBLE
        binding.toolbar.imageViewSelectStore.setOnClickListener(this)
        binding.toolbar.imageViewProfile.setOnClickListener(this)
        binding.profileMenuLayout.logoutLayout.setOnClickListener(this)
    }

    private fun setMenuRecyclerView() {
        val dashboardMenuList =
            AppUtils.getDashboardMenuList(AppUtils.getArrayListFromJson(this, R.raw.dashboard))

        binding.recyclerviewDashboard.layoutManager = GridLayoutManager(this, 3)
        val mAdapter = DashboardMenuListAdapter(this, dashboardMenuList) { _, item ->
            val intent = Intent(this@DashboardActivity, DetailsActivity::class.java)
            intent.putExtra(AppConstant.SELECTED_DASHBOARD_ITEM_KEY, item.id)
            startActivity(intent)
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
        }
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
                if (AppUtils.isNetworkAvailable(this)) {
                    val intent =
                        Intent(this@DashboardActivity, SearchStoreListingActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
                } else {
                    AppUtils.showInternetAlertDialog(this)
                }
            }
            binding.toolbar.imageViewProfile -> {
                isProfileMenuVisible = if (isProfileMenuVisible) {
                    AppUtils.hideProfileOptionsView(binding.profileMenuLayout.menuParentLayout)
                    binding.toolbar.profileImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_person_24
                        )
                    )
                    false
                } else {
                    AppUtils.showProfileOptionsView(binding.profileMenuLayout.menuParentLayout)
                    binding.toolbar.profileImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_close_24
                        )
                    )
                    true
                }
            }
            binding.profileMenuLayout.logoutLayout -> {
                if (AppUtils.isNetworkAvailable(this)) {
                    showAlertDialog {
                        setTitle(context.resources.getString(R.string.logout_alert_title))
                        setMessage(context.resources.getString(R.string.logout_alert_message))
                        positiveButtonClick(context.resources.getString(R.string.ok)) {
                            preferenceManager.clearAllData()
                            val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                        }
                        negativeButtonClick(context.resources.getString(R.string.cancel)) {}
                    }
                } else {
                    AppUtils.showInternetAlertDialog(this)
                }
            }
        }
    }
}