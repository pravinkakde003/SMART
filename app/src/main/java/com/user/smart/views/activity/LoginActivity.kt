package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.activity.viewModels
import com.user.smart.R
import com.user.smart.databinding.ActivityLoginBinding
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.*
import com.user.smart.utils.AppUtils.isNetworkAvailable
import com.user.smart.views.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListener()
        observeLiveData()
    }

    private fun setClickListener() {
        binding.txtPolicy.movementMethod = LinkMovementMethod.getInstance()
        binding.txtPolicy.setOnClickListener(this)
        binding.forgotPasswordButton.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)
    }

    private fun observeLiveData() {
        loginViewModel.loginLiveData.observe(this) {
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
                    it.data?.let { userResponse ->
                        preferenceManager.saveToken(userResponse.token)
                        val intent = Intent(this@LoginActivity, SelectStoreActivity::class.java)
                        startActivity(intent)
                        finish()
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.forgotPasswordButton -> {
                val intent = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            binding.loginButton -> {
                AppUtils.hideKeyboard(binding.root)
                val validationResult = validateUserInput()
                if (validationResult.first) {
                    if (isNetworkAvailable(this)) {
                        loginViewModel.callLoginAPI(
                            binding.emailTextField.editText?.text.toString(),
                            binding.passwordTextField.editText?.text.toString()
                        )
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
        val password = binding.passwordTextField.editText?.text.toString()
        return loginViewModel.validateCredentials(emailAddress, password)
    }
}