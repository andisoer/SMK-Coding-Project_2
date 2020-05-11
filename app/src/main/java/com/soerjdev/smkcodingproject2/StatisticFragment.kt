package com.soerjdev.smkcodingproject2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_graph.*

/**
 * A simple [Fragment] subclass.
 */
class StatisticFragment : Fragment() {

    private lateinit var graphViewPagerAdapter : StatisticViewPagerAdapter

    private val titleTab = arrayOf("Indonesia", "Dunia")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        graphViewPagerAdapter = StatisticViewPagerAdapter(this)
        viewPagerGraph.adapter = graphViewPagerAdapter

        TabLayoutMediator(tabLayoutGraph, viewPagerGraph,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titleTab[position]
            }
        ).attach()

        super.onViewCreated(view, savedInstanceState)
    }
}
