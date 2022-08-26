package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import com.user.smart.R
import com.user.smart.databinding.ActivitySelectStoreBinding
import com.user.smart.models.StoreListResponseItem
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.*
import com.user.smart.views.viewmodel.StoreListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectStoreActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySelectStoreBinding
    private val storeListViewModel: StoreListViewModel by viewModels()

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.nextButton.setOnClickListener(this)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        observeBinding()
        storeListViewModel.callGetStoreListAPI()
    }

    private fun observeBinding() {
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
                            val adapter = ArrayAdapter(
                                this,
                                R.layout.dropdown_list_item,
                                storeList
                            )

                            (binding.textField.editText as? AutoCompleteTextView)?.setAdapter(
                                adapter
                            )
                            (binding.textField.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
                                binding.nextButton.isEnabled = true
                                val selectedTeam: StoreListResponseItem? = adapter.getItem(position)
                                val mObject: StoreListResponseItem? =
                                    storeList.singleOrNull { it.store_name == selectedTeam.toString() }
                                preferenceManager.saveSelectedStoreObject(mObject)
                            }
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
                val intent = Intent(this@SelectStoreActivity, DashboardActivity::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        }
    }
}