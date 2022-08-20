package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import com.user.smart.R
import com.user.smart.databinding.ActivitySelectStoreBinding
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.AppConstant.FROM_LOGIN_SCREEN_KEY
import com.user.smart.utils.positiveButtonClick
import com.user.smart.utils.showAlertDialog
import com.user.smart.views.viewmodel.StoreListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectStoreActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySelectStoreBinding
    var isFromLoginScreen = false
    private val storeListViewModel: StoreListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (null != intent && intent.hasExtra(FROM_LOGIN_SCREEN_KEY)) {
            isFromLoginScreen = intent.getBooleanExtra(FROM_LOGIN_SCREEN_KEY, false)
        }
        binding.nextButton.setOnClickListener(this)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        storeListViewModel.callGetStoreListAPI()

        storeListViewModel.storeListLiveData.observe(this) { responseData ->
            hideProgressDialog()
            when (responseData) {
                is NetworkResult.Loading -> {
                    showProgressDialog()
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
                            val itemList = mutableListOf<String>()
                            for (item in storeList) {
                                itemList.add(item.store_name)
                            }
                            val adapter = ArrayAdapter(this, R.layout.dropdown_list_item, itemList)
                            (binding.textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.nextButton -> {
                when {
                    isFromLoginScreen -> {
                        val intent = Intent(this@SelectStoreActivity, DashboardActivity::class.java)
                        startActivity(intent)
                    }
                }
                onBackPressed()
            }
        }
    }
}