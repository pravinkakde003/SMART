package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.user.smart.databinding.ActivityLoginSingupInfoBinding
import com.user.smart.utils.AppConstant.DATA_KEY
import com.user.smart.utils.AppConstant.LOGIN
import com.user.smart.utils.AppConstant.SIGN_UP

class LoginSignupInfoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginSingupInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSingupInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonLogin.setOnClickListener(this)
        binding.buttonCreateAccount.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.buttonLogin -> {
                val intent = Intent(this@LoginSignupInfoActivity, LoginActivity::class.java)
                intent.putExtra(DATA_KEY, LOGIN)
                startActivity(intent)
                finish()
            }
            binding.buttonCreateAccount -> {
                val intent = Intent(this@LoginSignupInfoActivity, LoginActivity::class.java)
                intent.putExtra(DATA_KEY, SIGN_UP)
                startActivity(intent)
                finish()
            }
        }
    }
}