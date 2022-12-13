package com.user.smart.views.fragments.poslive

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.user.smart.R
import com.user.smart.databinding.FragmentPosliveTransactionDetailsBinding
import com.user.smart.models.*
import com.user.smart.utils.AppConstant
import com.user.smart.utils.PreferenceManager
import com.user.smart.views.adapters.POSTransactionDetailsDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class POSLiveTransactionDetailsFragment : Fragment() {
    private var _binding: FragmentPosliveTransactionDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPosliveTransactionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        val posLiveDataResponseItem =
            arguments?.getSerializable(AppConstant.POS_LIVE_DATA_RESPONSE_ITEM_KEY) as POSLiveDataResponseItem
        setDataToView(posLiveDataResponseItem)
    }

    private fun setDataToView(posLiveDataResponseItem: POSLiveDataResponseItem?) {
        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreName.text = selectedStoreObject.store_name
        }
        if (null != posLiveDataResponseItem) {
            var dateTime =
                "${posLiveDataResponseItem.EventEndDate}  ${posLiveDataResponseItem.EventEndTime}"
            binding.txtDateTime.text = dateTime

            if (!posLiveDataResponseItem.CashierID.isNullOrEmpty()) {
                var empID = "Employee: ${posLiveDataResponseItem.CashierID}"
                binding.txtEmpID.text = empID
            } else {
                binding.txtEmpID.text = "Employee: -"
            }

            if (!posLiveDataResponseItem.RegisterID.isNullOrEmpty()) {
                var stationID = "Station: ${posLiveDataResponseItem.RegisterID}"
                binding.txtStation.text = stationID
            } else {
                binding.txtStation.text = "Station: -"
            }



            prepareDataList(posLiveDataResponseItem)

        } else {
            Log.e("POSLiveTransactionDetailsFragment", "Data not received")
        }
    }

    private fun prepareDataList(posLiveDataResponseItem: POSLiveDataResponseItem) {

        val posDetailsListItem = ArrayList<POSDetailsListItem>()

        val itemLinePresent =
            posLiveDataResponseItem.TransactionLine.any { it.ItemLine is ItemLine }
        if (itemLinePresent) {
            val itemLineList: List<TransactionLine> =
                posLiveDataResponseItem.TransactionLine.filter { it.ItemLine is ItemLine }
            itemLineList.forEach {
                posDetailsListItem.add(
                    POSDetailsListItem(
                        quantity = "x ${it.ItemLine.SalesQuantity.toFloat().toInt()}",
                        description = it.ItemLine.Description,
                        amount = it.ItemLine.SalesAmount
                    )
                )
            }
        }

        val fuelLinePresent =
            posLiveDataResponseItem.TransactionLine.any { it.FuelLine is FuelLine }
        if (fuelLinePresent) {
            val itemLineList: List<TransactionLine> =
                posLiveDataResponseItem.TransactionLine.filter { it.FuelLine is FuelLine }
            itemLineList.forEach {
                posDetailsListItem.add(
                    POSDetailsListItem(
                        quantity = it.FuelLine.SalesQuantity,
                        description = it.FuelLine.Description,
                        amount = it.FuelLine.SalesAmount
                    )
                )
            }
        }


//        val fuelPrepayLinePresent =
//            posLiveDataResponseItem.TransactionLine.any { it.FuelPrepayLine is FuelPrepayLine }
//        if (fuelPrepayLinePresent) {
//            val itemLineList: List<TransactionLine> =
//                posLiveDataResponseItem.TransactionLine.filter { it.FuelPrepayLine is FuelPrepayLine }
//            itemLineList.forEach {
//                posDetailsListItem.add(
//                    POSDetailsListItem(
//                        quantity = "",
//                        upc = "",
//                        dept = "",
//                        description = "",
//                        amount = it.FuelPrepayLine.SalesAmount
//                    )
//                )
//            }
//        }

        binding.txtSubTotalAmount.text = "$${posLiveDataResponseItem.TransactionTotalGrossAmount}"
        binding.txtTaxAmount.text = "$${posLiveDataResponseItem.TransactionTotalTaxNetAmount}"
        binding.txtTotalAmount.text = "$${posLiveDataResponseItem.TransactionTotalNetAmount}"
        setAdapter(posDetailsListItem)
    }

    private fun setAdapter(posDetailsListItem: ArrayList<POSDetailsListItem>) {
        binding.posTransactionDetailRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity())
        binding.posTransactionDetailRecyclerView.setHasFixedSize(true)
        var mAdapter = POSTransactionDetailsDataAdapter(posDetailsListItem)
        binding.posTransactionDetailRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.posTransactionDetailRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.posTransactionDetailRecyclerView.adapter = mAdapter
    }


    private fun setupToolbar() {
        binding.transactionDetailsToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )
        binding.transactionDetailsToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.transactionDetailsToolbar.txtDashboardTitle.text =
            resources.getString(R.string.transactions_details)
        binding.transactionDetailsToolbar.toolbarParentCardView.elevation = 8f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}