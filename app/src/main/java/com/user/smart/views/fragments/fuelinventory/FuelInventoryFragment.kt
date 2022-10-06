package com.user.smart.views.fragments.fuelinventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentFuelInventoryBinding
import com.user.smart.utils.PreferenceManager
import com.user.smart.utils.piechartutils.PieChart
import com.user.smart.utils.piechartutils.Slice
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class FuelInventoryFragment : Fragment() {

    private var _binding: FragmentFuelInventoryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFuelInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        val pieChart = PieChart(
            slices = provideSlices(), clickListener = null, sliceStartPoint = 0f, sliceWidth = 0f
        ).build()

        binding.fuelChart.setPieChart(pieChart)
        binding.fuelChart.showLegend(binding.legendLayout)
    }

    private fun provideSlices(): ArrayList<Slice> {
        return arrayListOf(
            Slice(
                Random.nextInt(100, 200).toFloat(),
                R.color.primary_color,
                "Regular"
            ),
            Slice(
                Random.nextInt(100, 200).toFloat(),
                R.color.red,
                "Super"
            ),
            Slice(
                Random.nextInt(100, 200).toFloat(),
                R.color.materialIndigo600,
                "Power"
            )
        )
    }

    private fun setupToolbar() {
        binding.fuelInventoryToolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24 // Drawable
            )
        )
        binding.fuelInventoryToolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fuelInventoryToolbar.txtDashboardTitle.text =
            resources.getString(R.string.fuel_inventory)
        binding.fuelInventoryToolbar.toolbarParentCardView.elevation = 8f
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