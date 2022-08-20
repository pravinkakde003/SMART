package com.user.smart.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentPosLiveBinding


class POSLiveFragment : Fragment() {
    private var _binding: FragmentPosLiveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPosLiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.trans_left_in,
                    R.anim.trans_right_out,
                    R.anim.trans_right_in, R.anim.trans_right_out
                )
                .add(R.id.fragmentContainer, POSClosingSalesFragment())
                .setReorderingAllowed(true)
                .addToBackStack("POSClosingSalesFragment")
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}