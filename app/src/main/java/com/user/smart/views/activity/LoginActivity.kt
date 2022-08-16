package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.user.smart.R
import com.user.smart.api.ApiService
import com.user.smart.api.RetrofitHelper
import com.user.smart.databinding.ActivityLoginBinding
import com.user.smart.repository.LoginRepository
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.AppConstant.FROM_LOGIN_SCREEN_KEY
import com.user.smart.utils.AppUtils
import com.user.smart.utils.positiveButtonClick
import com.user.smart.utils.showAlertDialog
import com.user.smart.views.viewmodel.LoginViewModel
import com.user.smart.views.viewmodel.LoginViewModelFactory

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val loginRepository = LoginRepository(apiService)
        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(loginRepository)
        )[LoginViewModel::class.java]

        binding.txtPolicy.movementMethod = LinkMovementMethod.getInstance()
        binding.txtPolicy.setOnClickListener(this)
        binding.forgotPasswordButton.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)

        loginViewModel.loginLiveData.observe(this, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                    showProgressDialog()
                }
                is NetworkResult.Error -> {
                    hideProgressDialog()
                    showAlertDialog {
                        setTitle(context.resources.getString(R.string.error))
                        setMessage(it.errorMessage?.message)
                        positiveButtonClick(context.resources.getString(R.string.ok)) { }
                    }
                }
                is NetworkResult.Success -> {
                    hideProgressDialog()
                    it.data?.let {
                        val intent = Intent(this@LoginActivity, SelectStoreActivity::class.java)
                        intent.putExtra(FROM_LOGIN_SCREEN_KEY, true)
                        startActivity(intent)
                        finish()
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                }
            }
        })
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.forgotPasswordButton -> {
                val intent = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            binding.loginButton -> {
                if (isInternetAvailable()) {
                    loginViewModel.callLoginAPI(
                        binding.emailTextField.editText?.text.toString(),
                        binding.passwordTextField.editText?.text.toString()
                    )
                } else {
                    AppUtils.showInternetAlertDialog(this)
                }
            }
        }
    }
}