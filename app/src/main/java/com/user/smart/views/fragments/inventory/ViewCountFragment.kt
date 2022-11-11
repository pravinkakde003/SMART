package com.user.smart.views.fragments.inventory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.user.smart.R
import com.user.smart.databinding.FragmentInventoryViewCountBinding
import com.user.smart.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewCountFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentInventoryViewCountBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryViewCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setClickListener()
        setDropDownAdapter()
    }

    private fun setClickListener() {
        binding.dateTextView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.dateTextView -> {
                val datePicker = getMaterialDatePicker(CalendarConstraints.Builder())
                datePicker.addOnPositiveButtonClickListener {
                    binding.dateTextView.text = datePicker.headerText.toString()
                }
                datePicker.show(childFragmentManager, "StartDateEndDateView")
            }
        }
    }


    private fun setDropDownAdapter() {
        val dropDownArrayList = resources.getStringArray(R.array.account_dropdown_array)
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, dropDownArrayList)
        (binding.dropDownView.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (binding.dropDownView.editText as? AutoCompleteTextView)?.setText(adapter.getItem(0), false)
        (binding.dropDownView.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
            val selectedTeam = adapter.getItem(position)
            Log.e("TAGG", "" + selectedTeam)
        }
    }

    private fun setupToolbar() {
        binding.accountFragmentToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24 // Drawable
            )
        )
        binding.accountFragmentToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.accountFragmentToolbar.txtDashboardTitle.text =
            resources.getString(R.string.view_count)
        binding.accountFragmentToolbar.toolbarParentCardView.elevation = 8f
        val selectedStoreObject = preferenceManager.getSelectedStoreObject()
        if (null != selectedStoreObject && !selectedStoreObject.store_name.isNullOrEmpty()) {
            binding.txtStoreName.text = selectedStoreObject.store_name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getMaterialDatePicker(constraintsBuilder: CalendarConstraints.Builder): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText(resources.getString(R.string.select_date))
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
    }

}