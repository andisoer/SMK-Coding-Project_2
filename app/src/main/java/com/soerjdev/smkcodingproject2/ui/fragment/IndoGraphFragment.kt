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
import com.soerjdev.smkcodingproject2.model.updatedata.UpdateData
import com.soerjdev.smkcodingproject2.utils.ApiUtils
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_indo_graph.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class IndoGraphFragment : Fragment() {

    private lateinit var updateData: UpdateData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_indo_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getIndoData()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun getIndoData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_GOV)

        val call = apiRequest.getUpdateData()
        call.enqueue(object : Callback<UpdateData> {
            override fun onFailure(call: Call<UpdateData>, t: Throwable) {
                pbLoadIndoGraph.visibility = View.GONE
                containerTimeoutIndoGraph.visibility = View.VISIBLE
            }
            override fun onResponse(call: Call<UpdateData>, response: Response<UpdateData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        updateData = response.body()!!
                        setDataToChart()
                    }
                }
            }
        })
//        val call = apiRequest.getSummaryIndoData()
//        call.enqueue(object : Callback<List<SummaryIndoDataItem>>{
//            override fun onFailure(call: Call<List<SummaryIndoDataItem>>, t: Throwable) {
//                pbLoadIndoGraph.visibility = View.GONE
//                containerTimeoutIndoGraph.visibility = View.VISIBLE
//            }
//
//            override fun onResponse(
//                call: Call<List<SummaryIndoDataItem>>,
//                response: Response<List<SummaryIndoDataItem>>
//            ) {
//                if (response.isSuccessful){
//                    if(response.body()?.size != 0){
//                        listIndoData = response.body()!!
//                        setDataToChart()
//                    }
//                }
//            }
//
//        })
    }

    private fun setDataToChart() {

        val positifIndo = updateData.update.total.jumlahPositif
        val recoveredIndo = updateData.update.total.jumlahPositif
        val deathIndo = updateData.update.total.jumlahMeninggal

//        for (i in listIndoData.indices){
//            positifIndo = NumberFormat.getInstance(Locale.getDefault()).parse(listIndoData[i].positif)?.toInt()!!
//            recoveredIndo = NumberFormat.getInstance(Locale.getDefault()).parse(listIndoData[i].sembuh)?.toInt()!!
//            deathIndo = NumberFormat.getInstance(Locale.getDefault()).parse(listIndoData[i].meninggal)?.toInt()!!
//        }

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

        entries.add(PieEntry(positifIndo.toFloat(), "Positif"))
        entries.add(PieEntry(recoveredIndo.toFloat(), "Sembuh"))
        entries.add(PieEntry(deathIndo.toFloat(), "Meninggal"))

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

        pbLoadIndoGraph.visibility = View.GONE
        pieChartIndoGraph.visibility = View.VISIBLE
        pieChartIndoGraph.animateY(1500, Easing.EaseInOutSine)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
