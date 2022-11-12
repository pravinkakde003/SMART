package com.user.smart.views.fragments.lottery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentTabLotteryConfirmBinding

class LotteryConfirmFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTabLotteryConfirmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabLotteryConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        binding.dateTextView1.setOnClickListener(this)
        binding.dateTextView2.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.dateTextView1 -> {
                navigateToDetailFragment()
            }
            binding.dateTextView2 -> {
                navigateToDetailFragment()
            }
        }
    }

    private fun navigateToDetailFragment() {
        val targetFragment = LotteryConfirmDetailsFragment()
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