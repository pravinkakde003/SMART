package com.user.smart.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.user.smart.databinding.FragmentTabBalanceSheetBinding
import java.util.*

class BalanceSheetFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTabBalanceSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabBalanceSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        binding.dateCalenderView.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.dateCalenderView -> {
                showMaterialDatePicker()
            }
        }
    }

    private fun showMaterialDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setCalendarConstraints(CalendarConstraints.Builder().build())
            .build()
        datePicker.addOnPositiveButtonClickListener {
            binding.startDateTextView.text = datePicker.headerText.toString()
            val startDateCalender = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            startDateCalender.time = Date(it)
        }
        datePicker.show(childFragmentManager, "MaterialDatePicker")
    }
}