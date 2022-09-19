package com.user.smart.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.user.smart.R
import com.user.smart.databinding.FragmentPosLiveBinding
import com.user.smart.models.POSLiveDataResponse
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.*
import com.user.smart.utils.AppUtils.getCurrentDate
import com.user.smart.utils.AppUtils.showToast
import com.user.smart.views.adapters.POSLiveDataAdapter
import com.user.smart.views.viewmodel.PosLiveDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class POSLiveFragment : Fragment() {
    private var _binding: FragmentPosLiveBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    private val posLiveDataViewModel: PosLiveDataViewModel by viewModels()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPosLiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        callGetPOSLiveDataAPI()
        observeBinding()
    }

    private fun setupToolbar() {
        binding.toolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )
        binding.toolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.toolbar.txtDashboardTitle.text = resources.getString(R.string.pos_live)
        binding.toolbar.toolbarParentCardView.elevation = 8f
    }

    private fun callGetPOSLiveDataAPI() {
        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (AppUtils.isNetworkAvailable(requireContext())) {
            posLiveDataViewModel.callGetPOSLiveDataListAPI(
                posLiveDataViewModel.getStoreID(selectedStoreObject),
                getCurrentDate()
            )
        } else {
            AppUtils.showInternetAlertDialog(requireContext())
        }
    }

    private fun observeBinding() {
        posLiveDataViewModel.posLiveDataListLiveData.observe(requireActivity()) { responseData ->
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
                        val posLiveDataList = responseData.data
                        if (posLiveDataList.size > 0) {
                            setAdapter(posLiveDataList)
                        } else {
                            binding.withDataLayout.visibility = View.GONE
                            binding.noDataLayout.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setAdapter(posLiveDataList: POSLiveDataResponse) {
        binding.posLiveDataRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.posLiveDataRecyclerView.setHasFixedSize(true)
        var mAdapter = POSLiveDataAdapter(posLiveDataList) {
            if (it.TransactionTotalGrossAmount != "0") {
                val bundle = Bundle()
                bundle.putParcelable(AppConstant.POS_LIVE_DATA_RESPONSE_ITEM_KEY, it)
                val targetFragment = POSLiveTransactionDetailsFragment()
                targetFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.trans_left_in,
                        R.anim.trans_right_out,
                        R.anim.trans_right_in, R.anim.trans_right_out
                    )
                    .add(R.id.fragmentContainer, targetFragment)
                    .setReorderingAllowed(true)
                    .addToBackStack("POSLiveTransactionDetailsFragment")
                    .commit()
            } else {
                requireContext().showToast("Nothing to show")
            }
        }
        binding.posLiveDataRecyclerView.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}