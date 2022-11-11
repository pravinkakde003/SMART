package com.user.smart.views.fragments.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentTransactionBinding
import com.user.smart.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransactionFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setClickListener()
    }

    private fun setClickListener() {
        binding.layoutOne.dataLayout.setOnClickListener(this)
        binding.layoutTwo.dataLayout.setOnClickListener(this)
    }

    private fun setupToolbar() {
        binding.transactionToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24 // Drawable
            )
        )
        binding.transactionToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.transactionToolbar.txtDashboardTitle.text =
            resources.getString(R.string.transactions)
        binding.transactionToolbar.toolbarParentCardView.elevation = 8f

        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreName.text = selectedStoreObject.store_name
        }
//        if (binding.dateSelectorView.getStartDateText().isNullOrEmpty()) {
//            Log.e("TAGG", "EMPTY DATE")
//        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.layoutOne.dataLayout, binding.layoutTwo.dataLayout -> {
                val targetFragment = TransactionDetailsFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.trans_left_in,
                        R.anim.trans_right_out,
                        R.anim.trans_right_in, R.anim.trans_right_out
                    )
                    .add(R.id.fragmentContainer, targetFragment)
                    .setReorderingAllowed(true)
                    .addToBackStack("SendToPosDetailsFragment")
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}