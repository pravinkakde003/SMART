package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.activity.viewModels
import com.user.smart.R
import com.user.smart.databinding.ActivityLoginBinding
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.AppUtils
import com.user.smart.utils.PreferenceManager
import com.user.smart.utils.positiveButtonClick
import com.user.smart.utils.showAlertDialog
import com.user.smart.views.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtPolicy.movementMethod = LinkMovementMethod.getInstance()
        binding.txtPolicy.setOnClickListener(this)
        binding.forgotPasswordButton.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)

        loginViewModel.loginLiveData.observe(this) {
            hideProgressDialog()
            when (it) {
                is NetworkResult.Loading -> {
                    showProgressDialog()
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