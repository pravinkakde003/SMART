package com.user.smart.views.fragments.purchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.user.smart.R
import com.user.smart.databinding.FragmentAddNewPurchasesBinding
import com.user.smart.databinding.FragmentDetailsBinding
import com.user.smart.views.adapters.TabViewPagerAdapter

class PurchasesDetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setTabBar()
    }

    private fun setTabBar() {
        val lotteryTabTitleList = resources.getStringArray(R.array.add_purchases_tab_title_array)
        var fragmentList: ArrayList<Fragment> =
            arrayListOf(ItemWiseDetailsPurchaseFragment(), ScanMultipleItemDetailsPurchaseFragment())
        val adapter = TabViewPagerAdapter(fragmentList, childFragmentManager, lifecycle)
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = lotteryTabTitleList[position]
        }.attach()
    }

    private fun setupToolbar() {
        binding.checkInOutToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )
        binding.checkInOutToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.checkInOutToolbar.txtDashboardTitle.text =
            resources.getString(R.string.purchases)
        binding.checkInOutToolbar.toolbarParentCardView.elevation = 8f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}