package com.user.smart.views.fragments.fuelinventory

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.user.smart.R
import com.user.smart.databinding.FragmentFuelInventoryBinding
import com.user.smart.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
        initPieChart()
        setDataToPieChart()
    }

    private fun initPieChart() {
        binding.fuelChart.setUsePercentValues(true)
        binding.fuelChart.description.text = ""
        binding.fuelChart.isDrawHoleEnabled = false
        binding.fuelChart.setTouchEnabled(false)
        binding.fuelChart.setDrawEntryLabels(false)
        binding.fuelChart.setUsePercentValues(true)
        binding.fuelChart.isRotationEnabled = false
        binding.fuelChart.setDrawEntryLabels(false)
        binding.fuelChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        binding.fuelChart.legend.position = Legend.LegendPosition.ABOVE_CHART_CENTER
        binding.fuelChart.legend.isWordWrapEnabled = true
        binding.fuelChart.legend.xEntrySpace = 25f
    }

    private fun setDataToPieChart() {
        binding.fuelChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(25f, "Super"))
        dataEntries.add(PieEntry(45f, "Regular"))
        dataEntries.add(PieEntry(30f, "Power"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#EA4335"))
        colors.add(Color.parseColor("#7366FF"))
        colors.add(Color.parseColor("#D8B655"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 1f
        dataSet.colors = colors
        binding.fuelChart.data = data
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.parseColor("#FFFFFF"))
        binding.fuelChart.animateY(1400)
        binding.fuelChart.invalidate()
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