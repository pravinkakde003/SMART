package com.user.smart.views.fragments.inventory

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.zxing.integration.android.IntentIntegrator
import com.user.smart.databinding.FragmentTabInventoryAdjustmentBinding

class InventoryAdjustmentFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTabInventoryAdjustmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabInventoryAdjustmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        binding.scanTextView.setOnClickListener(this)
        binding.dateTextView.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val zxingActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intentResult = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
            if (intentResult.contents != null) {
                binding.scanDataTextView.text = intentResult.contents
            }
        }

    override fun onClick(view: View?) {
        when (view) {
            binding.scanTextView -> {
                permissionSetup()
            }
            binding.dateTextView -> {
                val datePicker = getMaterialDatePicker(CalendarConstraints.Builder())
                datePicker.addOnPositiveButtonClickListener {
                    binding.dateTextView.text = datePicker.headerText.toString()
                }
                datePicker.show(childFragmentManager, "StartDateEndDateView")
            }
        }
    }

    private fun permissionSetup() {
        val permission =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(Manifest.permission.CAMERA)
        } else {
            initiateScanner()
        }
    }

    private fun initiateScanner() {
        val intentIntegrator = IntentIntegrator(activity)
        zxingActivityResultLauncher.launch(intentIntegrator.createScanIntent())
    }

    private val permissionsResultCallback =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            when (it) {
                true -> {
                    initiateScanner()
                }
                false -> {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun getMaterialDatePicker(constraintsBuilder: CalendarConstraints.Builder): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
    }
}