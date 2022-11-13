package com.user.smart.views.fragments.groups

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.user.smart.R
import com.user.smart.databinding.FragmentTabPriceBinding
import com.user.smart.models.GroupsListDataItem
import com.user.smart.utils.AppUtils
import com.user.smart.utils.showCustomEditTextAlertDialog
import com.user.smart.views.adapters.GroupsDataAdapter

class GroupsPriceTabFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTabPriceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabPriceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        val groupItemList =
            AppUtils.getGroupsDummyList(
                AppUtils.getArrayListFromJson(
                    requireActivity(),
                    R.raw.temp_group
                )
            )
        setAdapter(groupItemList)
    }

    private fun setAdapter(dataListItem: List<GroupsListDataItem>) {
        binding.groupsDataRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity())
        binding.groupsDataRecyclerView.setHasFixedSize(true)
        var mAdapter = GroupsDataAdapter(dataListItem)
        binding.groupsDataRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.groupsDataRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.groupsDataRecyclerView.adapter = mAdapter
    }

    private fun setClickListener() {
        binding.createNewPriceGroupButton.setOnClickListener(this)
        binding.searchTextField.setEndIconOnClickListener {
            AppUtils.hideKeyboard(binding.searchTextField)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}