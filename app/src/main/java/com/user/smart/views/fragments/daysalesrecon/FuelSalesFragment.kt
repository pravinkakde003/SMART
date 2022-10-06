package com.user.smart.views.fragments.daysalesrecon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentFuelSalesBinding
import com.user.smart.utils.AppConstant

class FuelSalesFragment : Fragment() {

    private var _binding: FragmentFuelSalesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFuelSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbarTitle =
            arguments?.getString(AppConstant.TOOLBAR_TITLE_KEY)
                ?: resources.getString(R.string.fuelSalesButtonText)
        setupToolbar(toolbarTitle)
    }

    private fun setupToolbar(toolbarTitle: String) {
        binding.fuelSalesToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )
        binding.fuelSalesToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fuelSalesToolbar.txtDashboardTitle.text = toolbarTitle
        binding.fuelSalesToolbar.toolbarParentCardView.elevation = 8f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}