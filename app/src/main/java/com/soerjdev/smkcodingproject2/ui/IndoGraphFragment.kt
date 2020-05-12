package com.soerjdev.smkcodingproject2.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.soerjdev.smkcodingproject2.R
import kotlinx.android.synthetic.main.fragment_indo_graph.*

/**
 * A simple [Fragment] subclass.
 */
class IndoGraphFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_indo_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setDataToChart()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setDataToChart() {
        val entries : ArrayList<PieEntry> = ArrayList()

        val pieColor = arrayListOf(
            context?.let {
                ContextCompat.getColor(it,
                    R.color.colorConfirmed
                )
            },
            context?.let {
                ContextCompat.getColor(it,
                    R.color.colorRecovered
                )
            },
            context?.let {
                ContextCompat.getColor(it,
                    R.color.colorDeath
                )
            }
        )

        entries.add(PieEntry(15321f, "Positif"))
        entries.add(PieEntry(4412f, "Sembuh"))
        entries.add(PieEntry(1540f, "Meninggal"))

        val pieDataSet = PieDataSet(entries, "Statistik Indonesia")
        pieDataSet.colors = pieColor
        pieDataSet.valueTextSize = 14f
        pieDataSet.valueTextColor = Color.WHITE

        val pieData = PieData(pieDataSet)

        val legend = pieChartIndoGraph.legend
        legend.textSize = 14f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT

        pieChartIndoGraph.description.isEnabled = true
        pieChartIndoGraph.extraTopOffset = -50f
        pieChartIndoGraph.data = pieData
        pieChartIndoGraph.invalidate()
    }

}
