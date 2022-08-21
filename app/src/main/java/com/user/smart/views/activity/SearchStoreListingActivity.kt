package com.user.smart.views.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.user.smart.R
import com.user.smart.databinding.ActivitySearchStoreBinding
import com.user.smart.models.GetStoreListResponse
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.CustomProgressDialog
import com.user.smart.utils.PreferenceManager
import com.user.smart.utils.positiveButtonClick
import com.user.smart.utils.showAlertDialog
import com.user.smart.views.adapters.SearchStoreListAdapter
import com.user.smart.views.viewmodel.StoreListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchStoreListingActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchStoreBinding
    private val storeListViewModel: StoreListViewModel by viewModels()

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    @Inject
    lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        storeListViewModel.callGetStoreListAPI()

        storeListViewModel.storeListLiveData.observe(this) { responseData ->
            progressDialog.hide()
            when (responseData) {
                is NetworkResult.Loading -> {
                    progressDialog.show()
                }
                is NetworkResult.Error -> {
                    showAlertDialog {
                        setTitle(context.resources.getString(R.string.error))
                        setMessage(responseData.errorMessage?.message)
                        positiveButtonClick(context.resources.getString(R.string.ok)) { }
                    }
                }
                is NetworkResult.Success -> {
                    responseData.data?.let {
                        val storeList = responseData.data
                        if (storeList.size > 0) {
                            setAdapter(storeList)
                        }
                    }
                }
            }
        }
    }

    private fun setAdapter(storeList: GetStoreListResponse) {
        binding.storeListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.storeListRecyclerView.setHasFixedSize(true)
        binding.storeListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.storeListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.storeListRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        var mAdapter = SearchStoreListAdapter(storeList) {
            preferenceManager.saveSelectedStoreObject(it)
            onBackPressed()
        }
        binding.storeListRecyclerView.adapter = mAdapter
    }

    private fun setToolbar() {
        binding.toolbar.title = resources.getString(R.string.select_store)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primary_color))
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}