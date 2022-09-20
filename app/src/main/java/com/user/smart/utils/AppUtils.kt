package com.user.smart.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.user.smart.R
import com.user.smart.models.DashboardMenuModel
import com.user.smart.models.POSLiveDataResponseItem
import com.user.smart.models.StoreListResponseItem
import com.user.smart.utils.AppConstant.APP_DATE_FORMAT
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.hypot

object AppUtils {

    fun getArrayListFromJson(context: Context, @RawRes resourceId: Int): String {
        lateinit var jsonString: String
        try {
            jsonString =
                context.resources.openRawResource(resourceId)
                    .bufferedReader()
                    .use { it.readText() }
        } catch (ioException: IOException) {
            Log.e("AppUtils", ioException.toString())
        }
        return jsonString
    }

    fun getDashboardMenuList(jsonString: String): List<DashboardMenuModel> {
        val listType = object : TypeToken<List<DashboardMenuModel>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    fun getDummyTransactionList(jsonString: String): List<POSLiveDataResponseItem> {
        val listType = object : TypeToken<List<POSLiveDataResponseItem>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    fun getStoreList(jsonString: String): List<StoreListResponseItem> {
        val listType = object : TypeToken<List<StoreListResponseItem>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    fun showProfileOptionsView(mView: View) {
        val cx = mView.width / 2
        val cy = mView.height / 2
        val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(mView, cx, cy, 0f, finalRadius)
        mView.visibility = View.VISIBLE
        anim.start()
    }

    fun hideProfileOptionsView(mView: View) {
        val cx = mView.width / 2
        val cy = mView.height / 2
        val initialRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(mView, cx, cy, initialRadius, 0f)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                mView.visibility = View.GONE
            }
        })
        anim.start()
    }

    fun showInternetAlertDialog(context: Context) {
        context.showAlertDialog {
            setTitle(context.resources.getString(R.string.internet_alert_title))
            setMessage(context.resources.getString(R.string.internet_alert_message))
            positiveButtonClick(context.resources.getString(R.string.ok)) { }
        }
    }

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun hideKeyboard(view: View) {
        try {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    fun getCurrentDate(): String {
        return SimpleDateFormat(APP_DATE_FORMAT).format(Calendar.getInstance().time)
    }

    fun Context.showToast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}