package com.user.smart.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentLotteryBinding
import com.user.smart.utils.PreferenceManager
import com.user.smart.views.adapters.LotteryViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LotteryFragment : Fragment() {

    private var _binding: FragmentLotteryBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLotteryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setTabBar()
    }

    private fun setTabBar() {
        val adapter = LotteryViewPagerAdapter(childFragmentManager)
        adapter.addFragment(LotteryConfirmFragment(), "Confirm")
        adapter.addFragment(LotteryActiveFragment(), "Active")
        adapter.addFragment(LotterySalesFragment(), "Sales")
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun setupToolbar() {
        binding.lotteryToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24 // Drawable
            )
        )
        binding.lotteryToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.lotteryToolbar.txtDashboardTitle.text = resources.getString(R.string.lottery)
        binding.lotteryToolbar.toolbarParentCardView.elevation = 8f

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