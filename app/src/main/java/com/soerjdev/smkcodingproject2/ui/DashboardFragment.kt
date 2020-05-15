package com.soerjdev.smkcodingproject2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.api.ApiEndPoints
import com.soerjdev.smkcodingproject2.api.apiRequest
import com.soerjdev.smkcodingproject2.api.httpClient
import com.soerjdev.smkcodingproject2.model.summaryindodata.SummaryIndoDataItem
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_item_country_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    private var listIndoData : List<SummaryIndoDataItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initView()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        containerNameProvinces.setOnClickListener {
            startActivity(Intent(context, AllProvinceDataActivity::class.java))
        }

        containerNameWorld.setOnClickListener {
            startActivity(Intent(context, AllCountryDataActivity::class.java))
        }

        getSummaryIndoData()
    }

    private fun getSummaryIndoData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient)

        val call = apiRequest.getSummaryIndoData()
        call.enqueue(object : Callback<List<SummaryIndoDataItem>>{
            override fun onFailure(call: Call<List<SummaryIndoDataItem>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<SummaryIndoDataItem>>,
                response: Response<List<SummaryIndoDataItem>>
            ) {
                if (response.isSuccessful){
                    if(response.body()?.size != 0){
                        listIndoData = response.body()!!
                        setData()
                    }
                }
            }
        })
    }

    private fun setData(){
        for (i in listIndoData.indices){
            tvPositifCountryDashboard.text = listIndoData[i].positif
            tvRecoveredCountryDashboard.text = listIndoData[i].sembuh
            tvDeathCountryDashboard.text = listIndoData[i].meninggal
        }
    }
}
