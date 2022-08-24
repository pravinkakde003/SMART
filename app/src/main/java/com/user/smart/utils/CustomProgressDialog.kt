package com.user.smart.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import com.user.smart.R
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CustomProgressDialog @Inject constructor(@ActivityContext context: Context) {

    private var dialog: Dialog
    private var progressViewTitle: MaterialTextView
    private var progressLottieView: CircularProgressIndicator

    fun show(title: String = "") {
        progressViewTitle.text = title
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }

    init {
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.custom_loading_dialog, null)
        progressViewTitle = view.findViewById(R.id.progressViewTitle)
        progressLottieView = view.findViewById(R.id.progressIndicator)
        dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(view)
    }
}