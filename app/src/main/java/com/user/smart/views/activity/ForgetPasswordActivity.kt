package com.user.smart.views.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.user.smart.R
import com.user.smart.databinding.ActivityForgetPasswordBinding
import com.user.smart.utils.AppUtils
import com.user.smart.utils.positiveButtonClick
import com.user.smart.utils.showAlertDialog
import com.user.smart.views.viewmodel.ForgetPasswordModel

class ForgetPasswordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityForgetPasswordBinding
    private val forgetPasswordViewModel: ForgetPasswordModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.sendButton -> {
                val validationResult = validateUserInput()
                if (validationResult.first) {
                    if (isInternetAvailable()) {

                    } else {
                        AppUtils.showInternetAlertDialog(this)
                    }
                } else {
                    showAlertDialog {
                        setTitle(context.resources.getString(R.string.error))
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

}