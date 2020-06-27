package com.soerjdev.smkcodingproject2.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.api.ApiEndPoints
import com.soerjdev.smkcodingproject2.api.apiRequest
import com.soerjdev.smkcodingproject2.api.httpClient
import com.soerjdev.smkcodingproject2.model.globalcasesummary.GlobalCasesSummary
import com.soerjdev.smkcodingproject2.utils.ApiUtils
import kotlinx.android.synthetic.*
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

    lateinit var globalCasesSummary: GlobalCasesSummary

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getWorldData()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun getWorldData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_MATHDROID)

        val call = apiRequest.getWorldSummaryData()
        call.enqueue(object : Callback<GlobalCasesSummary>{
            override fun onFailure(call: Call<GlobalCasesSummary>, t: Throwable) {
                pbLoadWorldGraph.visibility = View.GONE
                containerTimeoutWorldGraph.visibility = View.VISIBLE
            }

            override fun onResponse(
                call: Call<GlobalCasesSummary>,
                response: Response<GlobalCasesSummary>
            ) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        globalCasesSummary = response.body()!!
                        setDataToChart()
                    }
                }
            }
        })
    }

    /*
    private fun getWorlDataPositif() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_GOV)

        val call = apiRequest.getGlobalPositif()

        call.enqueue(object : Callback<GlobalPositif> {
            override fun onFailure(call: Call<GlobalPositif>, t: Throwable) {
                pbLoadWorldGraph.visibility = View.GONE
                containerTimeoutWorldGraph.visibility = View.VISIBLE
            }

            override fun onResponse(call: Call<GlobalPositif>, response: Response<GlobalPositif>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        positifWorld = response.body()!!.value
                        getWorldDataRecovered()
                    }
                }
            }
        })
    }

    private fun getWorldDataRecovered() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient)
        val call = apiRequest.getGlobalRecovered()

        call.enqueue(object : Callback<GlobalRecovered>{
            override fun onFailure(call: Call<GlobalRecovered>, t: Throwable) {
                pbLoadWorldGraph.visibility = View.GONE
                containerTimeoutWorldGraph.visibility = View.VISIBLE
            }

            override fun onResponse(
                call: Call<GlobalRecovered>,
                response: Response<GlobalRecovered>
            ) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        recoveredWorld = response.body()!!.value
                        getWorlDataDeath()
                    }
                }
            }
        })
    }

    private fun getWorlDataDeath() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient)
        val call = apiRequest.getGlobalDeath()

        call.enqueue(object : Callback<GlobalDeath> {
            override fun onFailure(call: Call<GlobalDeath>, t: Throwable) {
                pbLoadWorldGraph.visibility = View.GONE
                containerTimeoutWorldGraph.visibility = View.VISIBLE
            }

            override fun onResponse(call: Call<GlobalDeath>, response: Response<GlobalDeath>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        deathWorld = response.body()!!.value
                        setDataToChart()
                    }
                }
            }
        })
    }

     */


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

        val positifWorldNum = globalCasesSummary.confirmed.value
        val recoveredWorldNum = globalCasesSummary.recovered.value
        val deathWorldNum= globalCasesSummary.deaths.value

        entries.add(PieEntry(positifWorldNum.toFloat(), "Positif"))
        entries.add(PieEntry(recoveredWorldNum.toFloat(), "Sembuh"))
        entries.add(PieEntry(deathWorldNum.toFloat(), "Meninggal"))

        val pieDataSet = PieDataSet(entries, "Statistik Dunia")
        pieDataSet.colors = pieColor
        pieDataSet.valueTextSize = 14f
        pieDataSet.valueTextColor = Color.WHITE

        val pieData = PieData(pieDataSet)

        val legend = pieCharWorldGraph.legend
        legend.textSize = 14f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT

        pieCharWorldGraph.description.isEnabled = true
        pieCharWorldGraph.extraTopOffset = -50f
        pieCharWorldGraph.data = pieData
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
