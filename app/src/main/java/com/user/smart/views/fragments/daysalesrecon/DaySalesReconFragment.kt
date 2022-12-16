package com.user.smart.views.fragments.daysalesrecon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.user.smart.R
import com.user.smart.databinding.FragmentDaySalesReconBinding
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.*
import com.user.smart.views.viewmodel.DaySalesReconViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DaySalesReconFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDaySalesReconBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    private val daySalesReconViewModel: DaySalesReconViewModel by viewModels()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaySalesReconBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setClickListener()
        callGetPOSLiveDataAPI()
        observeBinding()
    }

    private fun callGetPOSLiveDataAPI() {
        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (AppUtils.isNetworkAvailable(requireContext())) {
            daySalesReconViewModel.callDailySalesReconAPI(
                daySalesReconViewModel.getStoreID(selectedStoreObject),
                AppUtils.getCurrentDate()
            )
        } else {
            AppUtils.showInternetAlertDialog(requireContext())
        }
    }

    private fun observeBinding() {
        daySalesReconViewModel.daySalesReconLiveData.observe(requireActivity()) { responseData ->
            progressDialog.hide()
            when (responseData) {
                is NetworkResult.Loading -> {
                    progressDialog.show()
                }
                is NetworkResult.Error -> {
                    activity?.showAlertDialog {
                        setTitle(context.resources.getString(R.string.error))
                        setMessage(responseData.errorMessage?.message)
                        positiveButtonClick(context.resources.getString(R.string.ok)) { }
                    }
                }
                is NetworkResult.Success -> {
                    responseData.data?.let {
                        val apiResponse = responseData.data
                        if (null != apiResponse) {
                            if (apiResponse.data.size > 0) {
                                Log.e("TAGG", Gson().toJson(apiResponse))
                            } else {
                                binding.withDataLayout.visibility = View.GONE
                                binding.noDataLayout.visibility = View.VISIBLE
                            }
                        } else {
                            activity?.showAlertDialog {
                                setTitle(context.resources.getString(R.string.error))
                                setMessage(resources.getString(R.string.generic_error_message))
                                positiveButtonClick(context.resources.getString(R.string.ok)) { }
                            }
                        }

                    }
                }
            }
        }
    }

    private fun setClickListener() {
        binding.dateCalenderView.setOnClickListener(this)
    }

    private fun setupToolbar() {
        binding.daySalesReconToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )
        binding.daySalesReconToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.daySalesReconToolbar.txtDashboardTitle.text =
            resources.getString(R.string.day_sales)
        binding.daySalesReconToolbar.toolbarParentCardView.elevation = 8f

        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreName.text = selectedStoreObject.store_name
        }

        binding.startDateTextView.text = AppUtils.getCurrentDate()
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

    override fun onClick(view: View?) {
        when (view) {
            binding.dateCalenderView -> {
                showMaterialDatePicker()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}