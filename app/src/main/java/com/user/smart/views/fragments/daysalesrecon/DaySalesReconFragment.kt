package com.user.smart.views.fragments.daysalesrecon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentDaySalesReconBinding
import com.user.smart.utils.AppConstant
import com.user.smart.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DaySalesReconFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDaySalesReconBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaySalesReconBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setClickListener()
    }

    private fun setClickListener() {
        binding.merchandiseSalesButton.setOnClickListener(this)
        binding.fuelSalesButton.setOnClickListener(this)
    }

    private fun setupToolbar() {
        binding.daySalesReconToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24 // Drawable
            )
        )
        binding.daySalesReconToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.daySalesReconToolbar.txtDashboardTitle.text =
            resources.getString(R.string.day_sales)
        binding.daySalesReconToolbar.toolbarParentCardView.elevation = 8f

        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreName.text = selectedStoreObject.store_name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.merchandiseSalesButton -> {
                val bundle = Bundle()
                bundle.putString(
                    AppConstant.TOOLBAR_TITLE_KEY,
                    resources.getString(R.string.merchandiseSalesButtonText)
                )
                val targetFragment = MerchandiseSalesFragment()
                targetFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.trans_left_in,
                        R.anim.trans_right_out,
                        R.anim.trans_right_in, R.anim.trans_right_out
                    )
                    .add(R.id.fragmentContainer, targetFragment)
                    .setReorderingAllowed(true)
                    .addToBackStack("MerchandiseSalesFragment")
                    .commit()
            }
            binding.fuelSalesButton -> {
                val bundle = Bundle()
                bundle.putString(
                    AppConstant.TOOLBAR_TITLE_KEY,
                    resources.getString(R.string.fuelSalesButtonText)
                )
                val targetFragment = FuelSalesFragment()
                targetFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.trans_left_in,
                        R.anim.trans_right_out,
                        R.anim.trans_right_in, R.anim.trans_right_out
                    )
                    .add(R.id.fragmentContainer, targetFragment)
                    .setReorderingAllowed(true)
                    .addToBackStack("FuelSalesFragment")
                    .commit()
            }
        }
    }
}