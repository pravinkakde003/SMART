package com.user.smart.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentPosliveTransactionDetailsBinding
import com.user.smart.models.POSLiveDataResponseItem
import com.user.smart.utils.AppConstant
import com.user.smart.utils.PreferenceManager
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
            arguments?.getParcelable<POSLiveDataResponseItem>(AppConstant.POS_LIVE_DATA_RESPONSE_ITEM_KEY)
        setDataToView(posLiveDataResponseItem)
    }

    private fun setDataToView(posLiveDataResponseItem: POSLiveDataResponseItem?) {
        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreNameDate.text = selectedStoreObject.store_name
        }
        if (null != posLiveDataResponseItem) {
            var storeName = binding.txtStoreNameDate.text.toString()
            var dateTime =
                "${posLiveDataResponseItem.EventEndDate}  ${posLiveDataResponseItem.EventEndTime}"
            val finalText = storeName + "\n" + dateTime
            binding.txtStoreNameDate.text = finalText
        } else {
            Log.e("POSLiveTransactionDetailsFragment", "Data not received")
        }
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
        binding.toolbar.txtDashboardTitle.text = resources.getString(R.string.transactions_details)
        binding.toolbar.toolbarParentCardView.elevation = 8f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}