package com.user.smart.views.fragments.daysalesrecon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.user.smart.R
import com.user.smart.databinding.FragmentDaySalesReconBinding
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.*
import com.user.smart.views.adapters.DayReconAdapter
import com.user.smart.views.adapters.DayReconViewItem
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

    private val dayReconRecyclerViewAdapter = DayReconAdapter()

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
        binding.daySalesReconRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = dayReconRecyclerViewAdapter
        }
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
                            if (apiResponse.data.isNotEmpty()) {
                                val homeItemsList = mutableListOf<DayReconViewItem>()
                                homeItemsList.add(DayReconViewItem.Title(title = "Merchandise Sales"))
                                homeItemsList.add(
                                    DayReconViewItem.SubTitle(
                                        subTitleOne = "Department",
                                        subTitleTwo = "Items Sold",
                                        subTitleThree = "Amount"
                                    )
                                )
                                if (!apiResponse.data[0].In.MCM.isNullOrEmpty()) {
                                    for (dataItem in apiResponse.data[0].In.MCM) {
                                        homeItemsList.add(
                                            DayReconViewItem.ListDataItem(
                                                dataItem.MerchandiseCodeDescription,
                                                String.format(
                                                    "%.2f",
                                                    dataItem.SalesQuantity.toFloat()
                                                ),
                                                dataItem.SalesAmount
                                            )
                                        )
                                    }
                                }
                                if (!apiResponse.data[0].In.MCM_total.isNullOrEmpty()) {
                                    for (dataItem in apiResponse.data[0].In.MCM_total) {
                                        homeItemsList.add(
                                            DayReconViewItem.Total(
                                                "Total Grocery",
                                                "",
                                                "" + dataItem.Original
                                            )
                                        )
                                    }
                                }

                                homeItemsList.add(DayReconViewItem.Title(title = "Sales Tax"))
                                if (!apiResponse.data[0].In.SalesTax.isNullOrEmpty()) {
                                    for (dataItem in apiResponse.data[0].In.SalesTax) {
                                        homeItemsList.add(
                                            DayReconViewItem.Total(
                                                "Total",
                                                "",
                                                "" + dataItem.Original
                                            )
                                        )
                                    }
                                }


                                homeItemsList.add(DayReconViewItem.Title(title = "Fuel Sold (Gallons)"))
                                if (!apiResponse.data[0].In.FGMVol.isNullOrEmpty()) {
                                    for (dataItem in apiResponse.data[0].In.FGMVol) {
                                        homeItemsList.add(
                                            DayReconViewItem.ListDataItem(
                                                dataItem.MerchandiseCodeDescription,
                                                "",
                                                dataItem.FuelGradeSalesVolume
                                            )
                                        )
                                    }
                                }
                                if (!apiResponse.data[0].In.FGM_total_volume.isNullOrEmpty()) {
                                    for (dataItem in apiResponse.data[0].In.FGM_total_volume) {
                                        homeItemsList.add(
                                            DayReconViewItem.Total(
                                                "Total Gas Volume",
                                                "",
                                                "" + dataItem.Original
                                            )
                                        )
                                    }
                                }




                                homeItemsList.add(DayReconViewItem.Title(title = "Fuel Sold (Amount)"))
                                if (!apiResponse.data[0].In.FGM.isNullOrEmpty()) {
                                    for (dataItem in apiResponse.data[0].In.FGM) {
                                        homeItemsList.add(
                                            DayReconViewItem.ListDataItem(
                                                dataItem.MerchandiseCodeDescription,
                                                "",
                                                dataItem.FuelGradeSalesAmount
                                            )
                                        )
                                    }
                                }
                                if (!apiResponse.data[0].In.FGM_total_amount.isNullOrEmpty()) {
                                    for (dataItem in apiResponse.data[0].In.FGM_total_amount) {
                                        homeItemsList.add(
                                            DayReconViewItem.Total(
                                                "Total Gas Amt Sold",
                                                "",
                                                "" + dataItem.Original
                                            )
                                        )
                                    }
                                }

                                homeItemsList.add(DayReconViewItem.Title(title = "Total in"))
                                if (!apiResponse.data[2].totalinamt.isNullOrEmpty()) {
                                    for (dataItem in apiResponse.data[2].totalinamt) {
                                        homeItemsList.add(
                                            DayReconViewItem.Total(
                                                "Total",
                                                "",
                                                "" + dataItem.Original
                                            )
                                        )
                                    }
                                }

                                dayReconRecyclerViewAdapter.items = homeItemsList
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