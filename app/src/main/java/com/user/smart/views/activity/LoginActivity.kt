package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.user.smart.R
import com.user.smart.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtPolicy.movementMethod = LinkMovementMethod.getInstance()
        binding.txtPolicy.setOnClickListener(this)
        binding.forgotPasswordButton.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.forgotPasswordButton -> {
                val intent = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            binding.loginButton -> {
                val intent = Intent(this@LoginActivity, SelectStoreActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        }
    }
}