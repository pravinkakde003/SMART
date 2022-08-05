package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.user.smart.R
import com.user.smart.databinding.ActivitySelectStoreBinding

class SelectStoreActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySelectStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                val intent = Intent(this@SelectStoreActivity, ForgetPasswordActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        }
    }
}