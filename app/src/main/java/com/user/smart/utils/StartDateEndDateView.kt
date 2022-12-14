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
import java.text.SimpleDateFormat
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
                val simpleFormat = SimpleDateFormat(
                    AppConstant.APP_CALENDER_DATE_FORMAT,
                    Locale.US
                )
                simpleFormat.timeZone = TimeZone.getTimeZone("UTC")
                mBinding.startDateTextView.text = simpleFormat.format(Date(it))

//                mBinding.startDateTextView.text = datePicker.headerText.toString()
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
                    val simpleFormat = SimpleDateFormat(
                        AppConstant.APP_CALENDER_DATE_FORMAT, Locale.US
                    )
                    simpleFormat.timeZone = TimeZone.getTimeZone("UTC")
                    mBinding.endDateTextView.text = simpleFormat.format(Date(it))
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

    public fun setStartDateText(inputString: String) {
        if (inputString.isNotEmpty()) {
            startDateCalender = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            mBinding.startDateTextView.text = inputString
            val date: Date = SimpleDateFormat(AppConstant.APP_CALENDER_DATE_FORMAT)
                .parse(inputString)
            startDateCalender.time = date
        }
    }

    public fun getEndDateTextView(): ConstraintLayout {
        return mBinding.endDateCalenderView
    }

    public fun getStartDateTextView(): ConstraintLayout {
        return mBinding.startDateCalenderView
    }
}