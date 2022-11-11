package com.user.smart.views.fragments.inventory

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.InputType
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
import com.user.smart.R
import com.user.smart.databinding.FragmentTabInventoryAdjustmentBinding
import com.user.smart.utils.EditTextWithLabel

class InventoryAdjustmentFragment : Fragment() {

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
        setupView()
        setClickListener()
    }

    private fun setupView() {
        binding.editTextDate.setEditTextEnable(false)
        binding.editTextDate.setEditTextHeader(resources.getString(R.string.date))
        binding.editTextDate.setEditTextEndIcon(R.drawable.ic_baseline_calendar_today_24)

        binding.editTextScan.setEditTextEnable(false)
        binding.editTextScan.setEditTextHeader(resources.getString(R.string.scan_code))
        binding.editTextScan.setEditTextEndIcon(R.drawable.ic_baseline_document_scanner_24)
        binding.editTextScan.setEditTextEndIconHintText(resources.getString(R.string.scan))

        binding.editTextQuantity.setEditTextEnable(true)
        binding.editTextQuantity.setEditTextHeader(resources.getString(R.string.quantity_text))
        binding.editTextQuantity.setEditTextInputType(InputType.TYPE_CLASS_NUMBER)

        binding.editTextCurrentQuantity.setEditTextEnable(true)
        binding.editTextCurrentQuantity.setEditTextHeader(resources.getString(R.string.current_quantity_text))
        binding.editTextCurrentQuantity.setEditTextInputType(InputType.TYPE_CLASS_NUMBER)
    }

    private fun setClickListener() {
        binding.editTextDate.setOnEndIconClickListener(object : EditTextWithLabel.IClickCallback {
            override fun onEndIconClicked() {
                val datePicker = getMaterialDatePicker(CalendarConstraints.Builder())
                datePicker.addOnPositiveButtonClickListener {
                    binding.editTextDate.setEditTextValue(datePicker.headerText.toString())
                }
                datePicker.show(childFragmentManager, "StartDateEndDateView")
            }
        })

        binding.editTextScan.setOnEndIconClickListener(object : EditTextWithLabel.IClickCallback {
            override fun onEndIconClicked() {
                permissionSetup()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val zxingActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intentResult = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
            if (intentResult.contents != null) {
                binding.editTextScan.setEditTextValue(intentResult.contents)
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
            .setTitleText(resources.getString(R.string.select_date))
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
    }
}