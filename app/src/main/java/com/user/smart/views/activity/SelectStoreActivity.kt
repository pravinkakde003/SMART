package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.user.smart.R
import com.user.smart.databinding.ActivitySelectStoreBinding
import com.user.smart.utils.AppConstant.FROM_LOGIN_SCREEN_KEY

class SelectStoreActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySelectStoreBinding
    var isFromLoginScreen = false

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
        val items = listOf(
            "Fultondale Chevron 7017006",
            "Fultondale Chevron 7017006 2",
            "Fultondale Chevron 7017006 3",
            "Fultondale Chevron 7017006 4"
        )
        val adapter = ArrayAdapter(this, R.layout.dropdown_list_item, items)
        (binding.textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
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