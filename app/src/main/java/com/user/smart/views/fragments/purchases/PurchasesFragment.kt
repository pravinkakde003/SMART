package com.user.smart.views.fragments.purchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.user.smart.R
import com.user.smart.databinding.FragmentPurchasesBinding
import com.user.smart.utils.PreferenceManager
import com.user.smart.views.adapters.TabViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PurchasesFragment : Fragment() {

    private var _binding: FragmentPurchasesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchasesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setTabBar()
    }

    private fun setTabBar() {
        val lotteryTabTitleList = resources.getStringArray(R.array.purchases_tab_title_array)
        var fragmentList: ArrayList<Fragment> =
            arrayListOf(UnRetailedFragment(), PurchasesTabFragment())
        val adapter = TabViewPagerAdapter(fragmentList, childFragmentManager, lifecycle)
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = lotteryTabTitleList[position]
        }.attach()
    }


    private fun setupToolbar() {
        binding.purchasesToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24 // Drawable
            )
        )
        binding.purchasesToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.purchasesToolbar.txtDashboardTitle.text = resources.getString(R.string.purchases)
        binding.purchasesToolbar.toolbarParentCardView.elevation = 8f

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