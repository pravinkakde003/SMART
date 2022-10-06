package com.user.smart.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.user.smart.R
import com.user.smart.utils.AppUtils.showToast

fun Context.showCustomEditTextAlertDialog(
    @StyleRes style: Int = 0,
    callback: (String) -> Unit
) {
    val builder = MaterialAlertDialogBuilder(this, style)
        .create()
    val view = LayoutInflater.from(this)
        .inflate(R.layout.custom_edittext_alert_dialog, null, false)
    val submitButton = view.findViewById<MaterialButton>(R.id.submitButton)
    val cancelButton = view.findViewById<MaterialButton>(R.id.cancelButton)
    val editTextInputLayout = view.findViewById<TextInputLayout>(R.id.emailTextGroupName)

    builder.setView(view)
    submitButton.setOnClickListener {
        if (editTextInputLayout.editText?.text.toString().trim().isNotEmpty()) {
            callback.invoke(editTextInputLayout.editText?.text.toString())
            builder.dismiss()
        } else {
            AppUtils.hideKeyboard(view)
            this.showToast(getString(R.string.enter_group_name))
        }
    }
    cancelButton.setOnClickListener {
        builder.dismiss()
    }
    builder.setCanceledOnTouchOutside(false)
    builder.show()
}