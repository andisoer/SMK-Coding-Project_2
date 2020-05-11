package com.soerjdev.smkcodingproject2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class StatisticViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    private val tabCount = 2

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> {
                IndoGraphFragment()
            }
            1 -> {
                WorldGraphFragment()
            }
            else -> {
                IndoGraphFragment()
            }
        }
    }
}