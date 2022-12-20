package com.user.smart.views.fragments.fuelprice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentEditFuelPriceBinding
import com.user.smart.models.FuelPriceAPIResponseItem
import com.user.smart.utils.AppConstant
import com.user.smart.utils.AppUtils
import com.user.smart.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditFuelPriceFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentEditFuelPriceBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditFuelPriceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setClickListener()
        val fuelPriceDataItem =
            arguments?.getSerializable(AppConstant.FUEL_PRICE_DATA_KEY) as FuelPriceAPIResponseItem
        setDataToView(fuelPriceDataItem)
    }

    private fun setDataToView(fuelPriceDataItem: FuelPriceAPIResponseItem) {

        if (!fuelPriceDataItem.store_fuel_grade.isNullOrEmpty()) {
            binding.txtFuelGradeName.text = fuelPriceDataItem.store_fuel_grade_display_name
        }

        if (!fuelPriceDataItem.old_cash_price.isNullOrEmpty()) {
            binding.txtCurrentCashPrice.text = fuelPriceDataItem.old_cash_price
        }

        if (!fuelPriceDataItem.new_cash_price.isNullOrEmpty()) {
            binding.txtNewCashPrice.text = fuelPriceDataItem.new_cash_price
        }

        if (!fuelPriceDataItem.old_credit_price.isNullOrEmpty()) {
            binding.txtCurrentCreditPrice.text = fuelPriceDataItem.old_credit_price
        }

        if (!fuelPriceDataItem.new_credit_price.isNullOrEmpty()) {
            binding.txtNewCreditPrice.text = fuelPriceDataItem.new_credit_price
        }
        if (!fuelPriceDataItem.updatedAt.isNullOrEmpty()) {
            val convertedDate = AppUtils.formatDisplayDate(fuelPriceDataItem.updatedAt)
            binding.txtLastModified.text = convertedDate
        }
    }

    private fun setClickListener() {
        binding.saveButton.setOnClickListener(this)
    }

    private fun setupToolbar() {
        binding.sendToPosToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )
        binding.sendToPosToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.sendToPosToolbar.txtDashboardTitle.text =
            resources.getString(R.string.edit_fuel_price)
        binding.sendToPosToolbar.toolbarParentCardView.elevation = 8f
        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreName.text = selectedStoreObject.store_name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.saveButton -> {

            }
        }
    }
}