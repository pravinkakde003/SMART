package com.user.smart.views.fragments.fuelprice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.user.smart.R
import com.user.smart.databinding.FragmentFuelPriceBinding
import com.user.smart.models.FuelPriceAPIResponseItem
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.*
import com.user.smart.views.adapters.FuelPriceAdapter
import com.user.smart.views.viewmodel.FuelPriceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FuelPriceFragment : Fragment() {

    private var _binding: FragmentFuelPriceBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    private val fuelPriceViewModel: FuelPriceViewModel by viewModels()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFuelPriceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeBinding()
        callGetFuelPriceAPI()
    }

    private fun setupToolbar() {
        binding.fuelPriceToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24 // Drawable
            )
        )
        binding.fuelPriceToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fuelPriceToolbar.txtDashboardTitle.text = resources.getString(R.string.fuel_price)
        binding.fuelPriceToolbar.toolbarParentCardView.elevation = 8f

        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreName.text = selectedStoreObject.store_name
        }
    }

    private fun callGetFuelPriceAPI() {
        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (AppUtils.isNetworkAvailable(requireContext())) {
            fuelPriceViewModel.callGetFuelPriceLiveDataAPI(
                fuelPriceViewModel.getStoreID(
                    selectedStoreObject
                ),
            )
        } else {
            AppUtils.showInternetAlertDialog(requireContext())
        }
    }

    private fun observeBinding() {
        fuelPriceViewModel.fuelPriceListLiveData.observe(requireActivity()) { responseData ->
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
                        val fuelPriceList = responseData.data.toMutableList()
                        if (fuelPriceList.size > 0) {
                            setAdapter(fuelPriceList)
                        } else {
                            binding.withDataLayout.visibility = View.GONE
                            binding.noDataLayout.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setAdapter(fuelPriceList: MutableList<FuelPriceAPIResponseItem>) {
        binding.fuelPriceRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.fuelPriceRecyclerView.setHasFixedSize(true)
        var mAdapter = FuelPriceAdapter(fuelPriceList) {
            val targetFragment = EditFuelPriceFragment()
            val bundle = Bundle()
            bundle.putSerializable(AppConstant.FUEL_PRICE_DATA_KEY, it)
            targetFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.trans_left_in,
                    R.anim.trans_left_out,
                    R.anim.trans_right_in,
                    R.anim.trans_right_out,
                )
                .replace(R.id.fragmentContainer, targetFragment)
                .setReorderingAllowed(true)
                .addToBackStack("FuelPriceFragment")
                .commit()
        }
        binding.fuelPriceRecyclerView.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}