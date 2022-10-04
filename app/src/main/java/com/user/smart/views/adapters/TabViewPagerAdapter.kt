package com.user.smart.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabViewPagerAdapter(
    private val listFragment: ArrayList<Fragment>,
    supportFragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(supportFragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}