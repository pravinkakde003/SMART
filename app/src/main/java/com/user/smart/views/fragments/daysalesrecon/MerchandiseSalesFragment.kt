package com.user.smart.views.fragments.daysalesrecon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentMerchandiseSalesBinding
import com.user.smart.utils.AppConstant
import com.user.smart.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MerchandiseSalesFragment : Fragment() {

    private var _binding: FragmentMerchandiseSalesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMerchandiseSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbarTitle =
            arguments?.getString(AppConstant.TOOLBAR_TITLE_KEY)
                ?: resources.getString(R.string.merchandiseSalesButtonText)
        setupToolbar(toolbarTitle)
        setDropDownAdapter()
    }

    private fun setDropDownAdapter() {
        val dropDownArrayList = resources.getStringArray(R.array.merchandise_sales_dropdown_array)
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, dropDownArrayList)
        (binding.dropDownView.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (binding.dropDownView.editText as? AutoCompleteTextView)?.setText(adapter.getItem(0), false)
        (binding.dropDownView.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
            val selectedTeam = adapter.getItem(position)
            Log.e("TAGG", "" + selectedTeam)
        }
    }

    private fun setupToolbar(toolbarTitle: String) {
        binding.merchandiseSalesToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )
        binding.merchandiseSalesToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.merchandiseSalesToolbar.txtDashboardTitle.text = toolbarTitle
        binding.merchandiseSalesToolbar.toolbarParentCardView.elevation = 8f

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