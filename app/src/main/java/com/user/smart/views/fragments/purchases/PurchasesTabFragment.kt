package com.user.smart.views.fragments.purchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentTabPurchasesTabBinding

class PurchasesTabFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTabPurchasesTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabPurchasesTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        binding.addNewPurchasesButton.setOnClickListener(this)
        binding.dateTextView1.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.addNewPurchasesButton -> {
                val bundle = Bundle()
                val targetFragment = AddNewPurchasesFragment()
                targetFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.trans_left_in,
                        R.anim.trans_right_out,
                        R.anim.trans_right_in, R.anim.trans_right_out
                    )
                    .add(R.id.fragmentContainer, targetFragment)
                    .setReorderingAllowed(true)
                    .addToBackStack("AddNewPurchasesFragment")
                    .commit()
            }
            binding.dateTextView1 -> {
                navigateToDetailFragment()
            }
        }
    }

    private fun navigateToDetailFragment() {
        val targetFragment = PurchasesDetailsFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.trans_left_in,
                R.anim.trans_right_out,
                R.anim.trans_right_in, R.anim.trans_right_out
            )
            .add(R.id.fragmentContainer, targetFragment)
            .setReorderingAllowed(true)
            .addToBackStack("PurchasesDetailsFragment")
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}