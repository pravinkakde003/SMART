package com.user.smart.views.fragments.groups

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.user.smart.databinding.FragmentTabPromoBinding
import com.user.smart.utils.AppUtils
import com.user.smart.utils.showCustomEditTextAlertDialog

class GroupsPromoTabFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTabPromoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabPromoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        binding.createNewPriceGroupButton.setOnClickListener(this)
        binding.searchTextField.setEndIconOnClickListener {
            AppUtils.hideKeyboard(binding.searchTextField)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.createNewPriceGroupButton -> {
                requireActivity().showCustomEditTextAlertDialog {
                    Log.e("TAGG", it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}