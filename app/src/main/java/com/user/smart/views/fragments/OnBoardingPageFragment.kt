package com.user.smart.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.user.smart.databinding.FragmentOnboardingPageBinding
import com.user.smart.models.OnBoardingModel

class OnBoardingPageFragment : Fragment() {

    val PAGE_DATA = "page_data"
    private var page: OnBoardingModel? = null

    private var _binding: FragmentOnboardingPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            page = it.getSerializable(PAGE_DATA) as OnBoardingModel?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateViews()
    }

    private fun updateViews() {
        page?.let {
            _binding?.onBoardingTitle?.text = it.heading
            _binding?.onBoardingDescription?.text = it.desc
            _binding?.imageViewOnBoarding?.setImageResource(it.imageRes)
        }
    }

    companion  object {
        @JvmStatic
        fun newInstance(page: OnBoardingModel) =
            OnBoardingPageFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PAGE_DATA, page)
                }
            }
    }
}