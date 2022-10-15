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
import com.google.zxing.integration.android.IntentIntegrator
import com.user.smart.databinding.FragmentTabInventoryCountBinding

class InventoryCountFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTabInventoryCountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabInventoryCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        binding.scanTextView.setOnClickListener(this)
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
}