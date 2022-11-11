package com.user.smart.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.user.smart.databinding.EditTextWithLabelLayoutBinding

class EditTextWithLabel(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {
    private var mBinding: EditTextWithLabelLayoutBinding
    private var clickCallback: IClickCallback? = null

    init {
        mBinding = EditTextWithLabelLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.endIconTextView.setOnClickListener {
            clickCallback?.onEndIconClicked()
        }
    }

    fun setOnEndIconClickListener(itemClick: IClickCallback) {
        this.clickCallback = itemClick
    }

    fun setEditTextHeader(headerText: String) {
        mBinding.editTextHeader.text = headerText
    }

    fun setEditTextInputType(inputType: Int) {
        mBinding.editTextData.inputType = inputType
    }

    fun setEditTextEnable(isEnable: Boolean) {
        mBinding.editTextData.isEnabled = isEnable
    }

    fun setEditTextClickable(isClickable: Boolean) {
        mBinding.editTextData.isClickable = isClickable
    }

    fun setEditTextValue(inputText: String) {
        mBinding.editTextData.setText(inputText)
    }

    fun getEditTextValue(): CharSequence? {
        return mBinding.editTextData.text
    }

    fun setEditTextEndIcon(endIcon: Int) {
        mBinding.endIconTextView.visibility = View.VISIBLE
        mBinding.endIconTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, endIcon, 0);
    }

    fun setEditTextEndIconHintText(hintText: String) {
        mBinding.endIconTextView.visibility = View.VISIBLE
        mBinding.endIconTextView.text = hintText
    }

    interface IClickCallback {
        fun onEndIconClicked()
    }
}
