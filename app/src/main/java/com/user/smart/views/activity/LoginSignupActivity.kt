package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.user.smart.R
import com.user.smart.databinding.ActivityLoginSignupBinding
import com.user.smart.utils.AppConstant.DATA_KEY
import com.user.smart.utils.AppConstant.LOGIN

class LoginSignupActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (null != intent && intent.hasExtra(DATA_KEY)) {
            setupView(intent.getStringExtra(DATA_KEY))
        }
        binding.buttonSignup.setOnClickListener(this)
        binding.textViewSignInSignup.setOnClickListener(this)
    }

    private fun setupView(viewType: String?) {
        when {
            viewType.contentEquals(LOGIN) -> {
                setupLoginView()
            }
            else -> {
                setupSignUpView()
            }
        }
    }

    private fun setupSignUpView() {
        binding.textFieldName.visibility = View.VISIBLE
        binding.textFieldEmail.visibility = View.VISIBLE
        binding.textFieldPassword.visibility = View.VISIBLE
        binding.textFieldConfirmPassword.visibility = View.VISIBLE
        binding.buttonSignup.text = resources.getString(R.string.signup)
        binding.textViewSignInSignup.text = resources.getString(R.string.already_member)
        binding.textFieldPassword.setPadding(0, 0, 0, 0)
        binding.textFieldConfirmPassword.setPadding(0, 0, 0,
            resources.getDimensionPixelSize(R.dimen.dp_42)
        )
    }

    private fun setupLoginView() {
        binding.textFieldName.visibility = View.GONE
        binding.textFieldEmail.visibility = View.VISIBLE
        binding.textFieldPassword.visibility = View.VISIBLE
        binding.textFieldConfirmPassword.visibility = View.GONE
        binding.buttonSignup.text = resources.getString(R.string.sign_in)
        binding.textViewSignInSignup.text = resources.getString(R.string.new_member)
        binding.textFieldPassword.setPadding(
            0,
            0,
            0,
            resources.getDimensionPixelSize(R.dimen.dp_42)
        )
    }

    override fun onClick(view: View) {
        when (view) {
            binding.buttonSignup -> {
                startActivity(Intent(this@LoginSignupActivity, MainActivity::class.java))
                finish()
            }
            binding.textViewSignInSignup -> {
                if (binding.textViewSignInSignup.text.toString()
                        .contentEquals(resources.getString(R.string.new_member))
                ) {
                    setupSignUpView()
                } else {
                    setupLoginView()
                }
            }
        }
    }
}