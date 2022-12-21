package com.user.smart.views.fragments.fuelprice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.user.smart.R
import com.user.smart.databinding.FragmentEditFuelPriceBinding
import com.user.smart.models.FuelPriceAPIResponseItem
import com.user.smart.models.FuelPriceEditRequestBody
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.*
import com.user.smart.utils.AppUtils.showToast
import com.user.smart.views.viewmodel.EditFuelPriceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditFuelPriceFragment : Fragment(), View.OnClickListener {

    val TAG: String = EditFuelPriceFragment::class.java.simpleName

    private var fuelPriceDataItem: FuelPriceAPIResponseItem? = null
    private var _binding: FragmentEditFuelPriceBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    private val editFuelPriceViewModel: EditFuelPriceViewModel by viewModels()

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
        fuelPriceDataItem =
            arguments?.getSerializable(AppConstant.FUEL_PRICE_DATA_KEY) as FuelPriceAPIResponseItem
        if (null != fuelPriceDataItem) {
            setDataToView(fuelPriceDataItem!!)
            observeBinding()
        } else {
            Log.e(TAG, "Error in intent data")
        }
    }

    private fun observeBinding() {
        editFuelPriceViewModel.updateFuelPriceLiveData.observe(requireActivity()) { responseData ->
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
                        if (responseData.data.acknowledged) {
                            requireContext().showToast(getString(R.string.record_updated))
                            val targetFragment = FuelPriceFragment()
                            requireActivity().supportFragmentManager.beginTransaction()
                                .setCustomAnimations(
                                    R.anim.trans_right_in,
                                    R.anim.trans_right_out,
                                    R.anim.trans_left_in,
                                    R.anim.trans_right_out
                                )
                                .replace(R.id.fragmentContainer, targetFragment)
                                .setReorderingAllowed(true)
                                .addToBackStack("EditFuelPriceFragment")
                                .commit()
                        }
                    }
                }
            }
        }
    }

    private fun setDataToView(fuelPriceDataItem: FuelPriceAPIResponseItem) {

        if (!fuelPriceDataItem.store_fuel_grade.isNullOrEmpty()) {
            binding.txtFuelGradeName.setText(fuelPriceDataItem.store_fuel_grade_display_name)
        }

        if (!fuelPriceDataItem.old_cash_price.isNullOrEmpty()) {
            binding.txtCurrentCashPrice.setText("$${fuelPriceDataItem.old_cash_price}")
        }

        if (!fuelPriceDataItem.new_cash_price.isNullOrEmpty()) {
            val mString: String = fuelPriceDataItem.new_cash_price
            binding.editTextNewCashPrice.setText(mString)
        }

        if (!fuelPriceDataItem.old_credit_price.isNullOrEmpty()) {
            binding.txtCurrentCreditPrice.setText("$${fuelPriceDataItem.old_credit_price}")
        }

        if (!fuelPriceDataItem.new_credit_price.isNullOrEmpty()) {
            val mString: String = fuelPriceDataItem.new_credit_price
            binding.editTextNewCreditPrice.setText(mString)
        }
        if (!fuelPriceDataItem.updatedAt.isNullOrEmpty()) {
            val convertedDate = AppUtils.formatDisplayDate(fuelPriceDataItem.updatedAt)
            binding.txtLastModified.setText(convertedDate)
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
                if (AppUtils.isNetworkAvailable(requireContext())) {
                    editFuelPriceViewModel.callUpdateFuelPriceAPI(
                        FuelPriceEditRequestBody(
                            new_credit_price = binding.editTextNewCreditPrice.text.toString(),
                            new_cash_price = binding.editTextNewCashPrice.text.toString(),
                            _id = fuelPriceDataItem?._id ?: ""
                        )
                    )
                } else {
                    AppUtils.showInternetAlertDialog(requireContext())
                }
            }
        }
    }
}