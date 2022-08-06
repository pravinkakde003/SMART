package com.user.smart.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import kotlin.math.hypot

object AppUtils {

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
}