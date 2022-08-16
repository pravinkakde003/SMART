package com.user.smart.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.user.smart.network.ConnectionLiveData
import com.user.smart.utils.CustomProgressDialog


open class BaseActivity : AppCompatActivity() {
    private val isInternetAvailableData = MutableLiveData<Boolean>()

    private val progressDialog by lazy { CustomProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeInternetConnection()
    }

    private fun observeInternetConnection() {
        val connectionLiveData = ConnectionLiveData(applicationContext)
        connectionLiveData.observe(this) { isConnected ->
            if (isConnected) {
                isInternetAvailableData.postValue(true)
            } else {
                isInternetAvailableData.postValue(false)
            }
        }
    }

    fun isInternetAvailable(): Boolean {
        return isInternetAvailableData.value == true
    }

    fun showProgressDialog() {
        progressDialog.show()
    }

    fun hideProgressDialog() {
        progressDialog.hide()
    }
}