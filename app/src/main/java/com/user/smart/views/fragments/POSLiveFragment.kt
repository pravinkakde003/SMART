package com.user.smart.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.user.smart.R
import com.user.smart.databinding.FragmentPosLiveBinding
import com.user.smart.utils.CustomProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class POSLiveFragment : Fragment() {
    private var _binding: FragmentPosLiveBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPosLiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()


        binding.textView.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .setCustomAnimations(
//                    R.anim.trans_left_in,
//                    R.anim.trans_right_out,
//                    R.anim.trans_right_in, R.anim.trans_right_out
//                )
//                .add(R.id.fragmentContainer, POSClosingSalesFragment())
//                .setReorderingAllowed(true)
//                .addToBackStack("POSClosingSalesFragment")
//                .commit()
        }
    }

    private fun setupToolbar() {
        binding.toolbar.profileImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            )
        )
        binding.toolbar.imageViewProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.toolbar.txtDashboardTitle.text = resources.getString(R.string.pos_live)
        binding.toolbar.toolbarParentCardView.elevation = 8f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}