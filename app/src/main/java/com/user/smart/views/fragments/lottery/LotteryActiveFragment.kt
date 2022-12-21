package com.user.smart.views.fragments.lottery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.user.smart.R
import com.user.smart.databinding.FragmentTabLotteryActiveBinding
import com.user.smart.models.ActiveLotteryResponse
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.*
import com.user.smart.views.adapters.ActiveLotteryAdapter
import com.user.smart.views.viewmodel.ActiveLotteryViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LotteryActiveFragment : Fragment() {

    private var _binding: FragmentTabLotteryActiveBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private val activeLotteryViewModel: ActiveLotteryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabLotteryActiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callGetActiveLotteryDataAPI()
        observeBinding()
    }

    private fun observeBinding() {
        activeLotteryViewModel.activeLotteryListLiveData.observe(requireActivity()) { responseData ->
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
                        val activeLotteryResponse = responseData.data
                        if (activeLotteryResponse.size > 0) {
                            setAdapter(activeLotteryResponse)
                        } else {
                            binding.withDataLayout.visibility = View.GONE
                            binding.noDataLayout.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setAdapter(activeLotteryResponse: ActiveLotteryResponse) {
        binding.activeLotteryRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.activeLotteryRecyclerView.setHasFixedSize(true)
        var mAdapter = ActiveLotteryAdapter(activeLotteryResponse) {

        }
        binding.activeLotteryRecyclerView.adapter = mAdapter
    }

    private fun callGetActiveLotteryDataAPI() {
        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (AppUtils.isNetworkAvailable(requireContext())) {
            activeLotteryViewModel.callGetActiveLotteryListAPI(
                activeLotteryViewModel.getStoreID(
                    selectedStoreObject
                )
            )
        } else {
            AppUtils.showInternetAlertDialog(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}