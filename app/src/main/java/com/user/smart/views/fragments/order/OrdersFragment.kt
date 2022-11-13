package com.user.smart.views.fragments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentOrdersBinding
import com.user.smart.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setClickListener()
    }

    private fun setClickListener() {
        binding.addNewOrderButton.setOnClickListener(this)
        binding.dateTextView.setOnClickListener(this)
        binding.dateTextView2.setOnClickListener(this)
    }

    private fun setupToolbar() {
        binding.ordersToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24 // Drawable
            )
        )
        binding.ordersToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.ordersToolbar.txtDashboardTitle.text = resources.getString(R.string.order)
        binding.ordersToolbar.toolbarParentCardView.elevation = 8f

        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreName.text = selectedStoreObject.store_name
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.addNewOrderButton -> {
                navigateToDetailFragment()
            }
            binding.dateTextView -> {
                navigateToDetailFragment()
            }
            binding.dateTextView2 -> {
                navigateToDetailFragment()
            }
        }
    }

    private fun navigateToDetailFragment() {
        val targetFragment = AddNewOrderFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.trans_left_in,
                R.anim.trans_right_out,
                R.anim.trans_right_in, R.anim.trans_right_out
            )
            .add(R.id.fragmentContainer, targetFragment)
            .setReorderingAllowed(true)
            .addToBackStack("LotteryConfirmDetailsFragment")
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}