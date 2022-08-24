package com.user.smart.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.user.smart.databinding.DateRangeViewLayoutBinding


class StartDateEndDateView(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {
    private var mBinding: DateRangeViewLayoutBinding

    init {
        mBinding = DateRangeViewLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.startDateCalenderView.setOnClickListener {
            val datePicker = getMaterialDatePicker()
            datePicker.addOnPositiveButtonClickListener {
                mBinding.startDateTextView.text = datePicker.headerText.toString()
            }
            datePicker.show(
                (context as FragmentActivity).supportFragmentManager,
                "StartDateEndDateView"
            )
        }

        mBinding.endDateCalenderView.setOnClickListener {
            val datePicker = getMaterialDatePicker()
            datePicker.addOnPositiveButtonClickListener {
                mBinding.endDateTextView.text = datePicker.headerText.toString()
            }
            datePicker.show(
                (context as FragmentActivity).supportFragmentManager,
                "endDateCalenderView"
            )
        }
    }

    public fun getStartDate(): String {
        return if (mBinding.startDateTextView.text.toString().isNotEmpty()) {
            return mBinding.startDateTextView.text.toString()
        } else {
            ""
        }
    }

    public fun getEndDate(): String {
        return if (mBinding.endDateTextView.text.toString().isNotEmpty()) {
            return mBinding.endDateTextView.text.toString()
        } else {
            ""
        }
    }

    private fun getMaterialDatePicker(): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }
}