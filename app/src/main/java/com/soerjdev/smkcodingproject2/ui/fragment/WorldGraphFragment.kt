package com.soerjdev.smkcodingproject2.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.api.ApiEndPoints
import com.soerjdev.smkcodingproject2.api.apiRequest
import com.soerjdev.smkcodingproject2.api.httpClient
import com.soerjdev.smkcodingproject2.database.model.GlobalSummary
import com.soerjdev.smkcodingproject2.model.globalcasesummary.GlobalCasesSummary
import com.soerjdev.smkcodingproject2.utils.ApiUtils
import com.soerjdev.smkcodingproject2.viewmodel.GlobalSummaryViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_indo_graph.*
import kotlinx.android.synthetic.main.fragment_world_graph.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class WorldGraphFragment : Fragment() {

    private var positifWorld : String = ""
    private var recoveredWorld : String = ""
    private var deathWorld : String = ""

    private lateinit var globalSummaryViewModel: GlobalSummaryViewModel
    private lateinit var globalSummaryData: GlobalSummary


    lateinit var globalCasesSummary: GlobalCasesSummary

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        getWorldData()

        initView()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        globalSummaryViewModel = ViewModelProvider(this).get(GlobalSummaryViewModel::class.java)
        globalSummaryViewModel.globalSummaryData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { globalSummary ->
            globalSummary?.let {
                if(it != null){
                    globalSummaryData = it
                    setDataToChart()
                }
            }
        })
    }

//    private fun getWorldData() {
//        val httpClient = httpClient()
//        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_MATHDROID)
//
//        val call = apiRequest.getWorldSummaryData()
//        call.enqueue(object : Callback<GlobalCasesSummary>{
//            override fun onFailure(call: Call<GlobalCasesSummary>, t: Throwable) {
//                pbLoadWorldGraph.visibility = View.GONE
//                containerTimeoutWorldGraph.visibility = View.VISIBLE
//            }
//
//            override fun onResponse(
//                call: Call<GlobalCasesSummary>,
//                response: Response<GlobalCasesSummary>
//            ) {
//                if(response.isSuccessful){
//                    if(response.body() != null){
//                        globalCasesSummary = response.body()!!
//                        setDataToChart()
//                    }
//                }
//            }
//        })
//    }

    private fun setDataToChart() {
        val entries: ArrayList<PieEntry> = ArrayList()

        val pieColor = arrayListOf(
            context?.let {
                ContextCompat.getColor(
                    it,
                    R.color.colorConfirmed
                )
            },
            context?.let {
                ContextCompat.getColor(
                    it,
                    R.color.colorRecovered
                )
            },
            context?.let {
                ContextCompat.getColor(
                    it,
                    R.color.colorDeath
                )
            }
        )

        val positifWorldNum = globalSummaryData.confirmed
        val recoveredWorldNum = globalSummaryData.recovered
        val deathWorldNum= globalSummaryData.deaths

        entries.add(PieEntry(positifWorldNum.toFloat(), "Positif"))
        entries.add(PieEntry(recoveredWorldNum.toFloat(), "Sembuh"))
        entries.add(PieEntry(deathWorldNum.toFloat(), "Meninggal"))

        val pieDataSet = PieDataSet(entries, "Statistik Dunia")
        pieDataSet.colors = pieColor
        pieDataSet.valueTextSize = 14f
        pieDataSet.valueTextColor = Color.WHITE
        pieDataSet.valueFormatter = PercentFormatter(pieCharWorldGraph)

        val pieData = PieData(pieDataSet)

        val legend = pieCharWorldGraph.legend
        legend.textSize = 14f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT

        pieCharWorldGraph.description.isEnabled = true
        pieCharWorldGraph.extraTopOffset = -50f
        pieCharWorldGraph.data = pieData
        pieCharWorldGraph.setUsePercentValues(true)

        pieCharWorldGraph.invalidate()

        pbLoadWorldGraph.visibility = View.GONE
        pieCharWorldGraph.visibility = View.VISIBLE
        pieCharWorldGraph.animateY(1500, Easing.EaseInOutSine)
    }

    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()
    }

}
