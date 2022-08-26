package com.user.smart.views.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.user.smart.R
import com.user.smart.databinding.ActivityForgetPasswordBinding
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.AppUtils
import com.user.smart.utils.CustomProgressDialog
import com.user.smart.utils.positiveButtonClick
import com.user.smart.utils.showAlertDialog
import com.user.smart.views.viewmodel.ForgetPasswordModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgetPasswordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityForgetPasswordBinding
    private val forgetPasswordViewModel: ForgetPasswordModel by viewModels()

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListener()
        observeLiveData()
    }

    private fun setClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.sendButton.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.sendButton -> {
                AppUtils.hideKeyboard(binding.root)
                val validationResult = validateUserInput()
                if (validationResult.first) {
                    if (isInternetAvailable()) {
                        forgetPasswordViewModel.forgetPasswordAPI(
                            binding.emailTextField.editText?.text.toString(),
                        )
                    } else {
                        AppUtils.showInternetAlertDialog(this)
                    }
                } else {
                    showAlertDialog {
                        setTitle(context.resources.getString(R.string.success))
                        setMessage(validationResult.second)
                        positiveButtonClick(context.resources.getString(R.string.ok)) { }
                    }
                }
            }
        }
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val emailAddress = binding.emailTextField.editText?.text.toString()
        return forgetPasswordViewModel.validateCredentials(emailAddress)
    }

    private fun observeLiveData() {
        forgetPasswordViewModel.forgetPasswordLiveData.observe(this) {
            progressDialog.hide()
            when (it) {
                is NetworkResult.Loading -> {
                    progressDialog.show()
                }
                is NetworkResult.Error -> {
                    showAlertDialog {
                        setTitle(context.resources.getString(R.string.error))
                        setMessage(it.errorMessage?.message)
                        positiveButtonClick(context.resources.getString(R.string.ok)) { }
                    }
                }
                is NetworkResult.Success -> {
                    it.data?.let { response ->
                        showAlertDialog {
                            setTitle(context.resources.getString(R.string.error))
                            setMessage(response.message)
                            positiveButtonClick(context.resources.getString(R.string.ok)) { }
                        }
                    }
                }
            }
        }
    }

}