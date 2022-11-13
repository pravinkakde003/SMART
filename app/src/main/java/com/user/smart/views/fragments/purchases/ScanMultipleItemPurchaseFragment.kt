package com.user.smart.views.fragments.purchases

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentTabScanMultipleItemsBinding

class ScanMultipleItemPurchaseFragment : Fragment() {

    private var _binding: FragmentTabScanMultipleItemsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabScanMultipleItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDropDownAdapter()
    }

    private fun setDropDownAdapter() {
        // set drop down type
        val dropDownArrayList = resources.getStringArray(R.array.merchandise_sales_dropdown_array)
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, dropDownArrayList)
        (binding.dropDownView.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (binding.dropDownView.editText as? AutoCompleteTextView)?.setText(adapter.getItem(0), false)
        (binding.dropDownView.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
            val selectedTeam = adapter.getItem(position)
            Log.e("TAGG", "" + selectedTeam)
        }

        // set drop down payee
        val payeeDropDownArrayList = resources.getStringArray(R.array.payee_dropdown_array)
        val payeeAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_list_item, payeeDropDownArrayList)
        (binding.dropDownViewPayee.editText as? AutoCompleteTextView)?.setAdapter(payeeAdapter)
        (binding.dropDownViewPayee.editText as? AutoCompleteTextView)?.setText(
            payeeAdapter.getItem(
                0
            ), false
        )
        (binding.dropDownViewPayee.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
            val selectedTeam = payeeAdapter.getItem(position)
            Log.e("TAGG", "" + selectedTeam)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}