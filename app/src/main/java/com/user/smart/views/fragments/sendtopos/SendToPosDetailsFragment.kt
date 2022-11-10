package com.user.smart.views.fragments.sendtopos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentSendToPosDetailsBinding
import com.user.smart.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SendToPosDetailsFragment : Fragment() {

    private var _binding: FragmentSendToPosDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendToPosDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.sendToPosDetailsToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )
        binding.sendToPosDetailsToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.sendToPosDetailsToolbar.txtDashboardTitle.text =
            resources.getString(R.string.send_to_pos)
        binding.sendToPosDetailsToolbar.toolbarParentCardView.elevation = 8f
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