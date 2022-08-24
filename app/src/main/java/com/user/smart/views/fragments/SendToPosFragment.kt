package com.user.smart.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentSendToPosBinding

class SendToPosFragment : Fragment() {

    private var _binding: FragmentSendToPosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendToPosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
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
            if (binding.dateSelectorView.getStartDate().isNullOrEmpty()) {
                Log.e("TAGG", "EMPTY DATE")
            }
        }
        binding.toolbar.txtDashboardTitle.text = resources.getString(R.string.send_to_pos)
        binding.toolbar.toolbarParentCardView.elevation = 8f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}