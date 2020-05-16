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
import com.soerjdev.smkcodingproject2.model.GlobalDeath
import com.soerjdev.smkcodingproject2.model.GlobalPositif
import com.soerjdev.smkcodingproject2.model.GlobalRecovered
import com.soerjdev.smkcodingproject2.model.provinsidata.ProvinsiDataItem
import com.soerjdev.smkcodingproject2.model.summaryindodata.SummaryIndoDataItem
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_item_country_data.*
import kotlinx.android.synthetic.main.layout_shimmer_dashboard.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.full.memberProperties

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    private var listIndoData : List<SummaryIndoDataItem> = ArrayList()
    private var listProvince : List<ProvinsiDataItem> = ArrayList()

    private var globalPositif : String = ""
    private var globalRecovered : String = ""
    private var globalDeath : String = ""

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
                        getJatimData()
                    }
                }
            }
        })
    }

    private fun getJatimData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient)

        val call = apiRequest.getProvinceListData()
        call.enqueue(object : Callback<List<ProvinsiDataItem>>{
            override fun onFailure(call: Call<List<ProvinsiDataItem>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<List<ProvinsiDataItem>>,
                response: Response<List<ProvinsiDataItem>>
            ) {
                if(response.isSuccessful){
                    if(response.body()?.size != 0){
                        listProvince = response.body()!!
                        getGlobalDataPositif()
                    }
                }
            }
        })
    }

    private fun getGlobalDataPositif() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient)

        val call = apiRequest.getGlobalPositif()

        call.enqueue(object: Callback<GlobalPositif> {
            override fun onFailure(call: Call<GlobalPositif>, t: Throwable) {
            }

            override fun onResponse(call: Call<GlobalPositif>, response: Response<GlobalPositif>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        globalPositif = response.body()!!.value
                        getGlobalDataRecovered()
                    }
                }
            }
        })
    }

    private fun getGlobalDataRecovered() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient)

        val call2 = apiRequest.getGlobalRecovered()

        call2.enqueue(object: Callback<GlobalRecovered> {
            override fun onFailure(call: Call<GlobalRecovered>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<GlobalRecovered>,
                response: Response<GlobalRecovered>
            ) {
                if (response.isSuccessful){
                    if(response.body() != null){
                        globalRecovered = response.body()!!.value
                        getGlobalDataDeath()
                    }
                }
            }
        })
    }

    private fun getGlobalDataDeath() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient)

        val call = apiRequest.getGlobalDeath()

        call.enqueue(object: Callback<GlobalDeath> {
            override fun onFailure(call: Call<GlobalDeath>, t: Throwable) {
            }

            override fun onResponse(call: Call<GlobalDeath>, response: Response<GlobalDeath>) {
                if (response.isSuccessful){
                    if(response.body() != null){
                        globalDeath = response.body()!!.value
                        setData()
                    }
                }
            }
        })
    }

    private fun setData(){
        var provinsiPositif : String = ""
        var provinsiRecovered : String = ""
        var provinsiDeath : String = ""

        var provinsiPositifInt : Int = 0
        var provinsiRecoveredInt : Int = 0
        var provinsiDeathInt : Int = 0

        var positifIndo : Int = 0
        var recoveredIndo : Int = 0
        var deathIndo : Int = 0

        for (i in listIndoData.indices){
            tvPositifCountryDashboard.text = listIndoData[i].positif
            tvRecoveredCountryDashboard.text = listIndoData[i].sembuh
            tvDeathCountryDashboard.text = listIndoData[i].meninggal

            positifIndo =
                NumberFormat.getInstance(Locale.getDefault()).parse(listIndoData[i].positif)?.toInt()!!
            recoveredIndo =
                NumberFormat.getInstance(Locale.getDefault()).parse(listIndoData[i].sembuh)?.toInt()!!
            deathIndo =
                NumberFormat.getInstance(Locale.getDefault()).parse(listIndoData[i].meninggal)?.toInt()!!
        }

        for (i in listProvince.indices){
            val provinsiItem = listProvince[i]
            if(provinsiItem.attributes.provinsi.equals("Jawa Timur")){
                provinsiPositif = NumberFormat.getInstance(Locale.getDefault()).format(provinsiItem.attributes.kasusPosi)
                provinsiRecovered = NumberFormat.getInstance(Locale.getDefault()).format(provinsiItem.attributes.kasusSemb)
                provinsiDeath = NumberFormat.getInstance(Locale.getDefault()).format(provinsiItem.attributes.kasusMeni)

                provinsiPositifInt = provinsiItem.attributes.kasusPosi
                provinsiRecoveredInt  = provinsiItem.attributes.kasusSemb
                provinsiDeathInt  = provinsiItem.attributes.kasusMeni
            }
        }

        val persenPositif = (provinsiPositifInt.toDouble() / positifIndo) * 100
        val persenRecovered = (provinsiRecoveredInt.toDouble() / recoveredIndo) * 100
        val persenDeath = (provinsiDeathInt.toDouble() / deathIndo) * 100

        tvPersenPositifProvinceDashboard.text = "(${persenPositif.toInt()}%)"
        tvPersenRecoveredProvinceDashboard.text = "(${persenRecovered.toInt()}%)"
        tvPersenDeathProvinceDashboard.text = "(${persenDeath.toInt()}%)"

        tvPositifProvinceDashboard.text = provinsiPositif
        tvRecoveredProvinceDashboard.text = provinsiRecovered
        tvDeathProvinceDashboard.text = provinsiDeath

        tvPositifGlobalDashboard.text = globalPositif
        tvRecoveredGlobalDashboard.text = globalRecovered
        tvDeathGlobalDashboard.text = globalDeath

        containerDataDashboard.visibility = View.VISIBLE
        containerShimmerDashboard.visibility = View.GONE
        containerShimmerDashboard.stopShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
