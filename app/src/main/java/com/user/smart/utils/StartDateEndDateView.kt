package com.user.smart.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.user.smart.databinding.DateRangeViewLayoutBinding
import dagger.hilt.android.internal.managers.FragmentComponentManager
import java.util.*


class StartDateEndDateView(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {
    private var mBinding: DateRangeViewLayoutBinding
    private lateinit var startDateCalender: Calendar

    init {
        mBinding = DateRangeViewLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.startDateCalenderView.setOnClickListener {
            val datePicker = getMaterialDatePicker(CalendarConstraints.Builder())
            datePicker.addOnPositiveButtonClickListener {
                mBinding.startDateTextView.text = datePicker.headerText.toString()
                startDateCalender = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                startDateCalender.time = Date(it)
            }
            datePicker.show(
                getFragmentActivityContext(mBinding.startDateCalenderView).supportFragmentManager,
                "StartDateEndDateView"
            )
        }

        mBinding.endDateCalenderView.setOnClickListener {
            if (getStartDateText().isNotEmpty()) {
                val constraintsBuilder =
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.from(startDateCalender.timeInMillis))
                val datePicker = getMaterialDatePicker(constraintsBuilder)
                datePicker.addOnPositiveButtonClickListener {
                    mBinding.endDateTextView.text = datePicker.headerText.toString()
                }
                datePicker.show(
                    getFragmentActivityContext(mBinding.endDateCalenderView).supportFragmentManager,
                    "endDateCalenderView"
                )
            } else {
                Toast.makeText(context, "Please select date from", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getFragmentActivityContext(view: ConstraintLayout) =
        FragmentComponentManager.findActivity(view.context) as FragmentActivity

    private fun getMaterialDatePicker(constraintsBuilder: CalendarConstraints.Builder): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
    }

    public fun getStartDateText(): String {
        return if (mBinding.startDateTextView.text.toString().isNotEmpty()) {
            return mBinding.startDateTextView.text.toString()
        } else {
            ""
        }
    }

    public fun getEndDateText(): String {
        return if (mBinding.endDateTextView.text.toString().isNotEmpty()) {
            return mBinding.endDateTextView.text.toString()
        } else {
            ""
        }
    }
}