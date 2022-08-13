package com.user.smart.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.user.smart.R
import com.user.smart.models.DashboardMenuModel
import com.user.smart.models.StoreListModelItem
import java.io.IOException
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

    fun getStoreList(jsonString: String): List<StoreListModelItem> {
        val listType = object : TypeToken<List<StoreListModelItem>>() {}.type
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


}