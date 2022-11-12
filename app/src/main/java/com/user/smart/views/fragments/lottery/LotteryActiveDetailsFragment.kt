package com.user.smart.views.fragments.lottery

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
import com.user.smart.R
import com.user.smart.databinding.FragmentLotteryActiveDetailsBinding
import com.user.smart.utils.AppConstant
import com.user.smart.utils.PreferenceManager
import com.user.smart.views.fragments.inventory.ViewCountFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LotteryActiveDetailsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLotteryActiveDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLotteryActiveDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbarTitle =
            arguments?.getString(AppConstant.TOOLBAR_TITLE_KEY)
                ?: resources.getString(R.string.active)
        setupToolbar(toolbarTitle)
        setClickListener()
    }

    private fun setClickListener() {
        binding.scanTextView.setOnClickListener(this)
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

    private val zxingActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intentResult = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
            if (intentResult.contents != null) {
                binding.scanDataTextView.text = intentResult.contents
            }
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

    private fun setupToolbar(toolbarTitle: String) {
        binding.lotteryActiveDetailsToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )
        binding.lotteryActiveDetailsToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.lotteryActiveDetailsToolbar.txtDashboardTitle.text = toolbarTitle
        binding.lotteryActiveDetailsToolbar.toolbarParentCardView.elevation = 8f

        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreName.text = selectedStoreObject.store_name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}