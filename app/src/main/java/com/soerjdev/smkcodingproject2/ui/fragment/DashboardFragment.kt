package com.soerjdev.smkcodingproject2.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.api.ApiEndPoints
import com.soerjdev.smkcodingproject2.api.apiRequest
import com.soerjdev.smkcodingproject2.api.httpClient
import com.soerjdev.smkcodingproject2.model.globalcasesummary.GlobalCasesSummary
import com.soerjdev.smkcodingproject2.model.provdata.ProvData
import com.soerjdev.smkcodingproject2.model.updatedata.UpdateData
import com.soerjdev.smkcodingproject2.ui.AllCountryDataActivity
import com.soerjdev.smkcodingproject2.ui.AllProvinceDataActivity
import com.soerjdev.smkcodingproject2.utils.ApiUtils
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
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

    private var globalPositif : Int = 0
    private var globalRecovered : Int = 0
    private var globalDeath : Int = 0

    private lateinit var updateData : UpdateData
    private lateinit var provData: ProvData


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

        getUpdateData()

//        getSummaryIndoData()
    }

    private fun getUpdateData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_GOV)

        val call = apiRequest.getUpdateData()

        call.enqueue(object: Callback<UpdateData> {
            override fun onFailure(call: Call<UpdateData>, t: Throwable) {
                containerTimeoutDashboard.visibility = View.VISIBLE
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
            }

            override fun onResponse(call: Call<UpdateData>, response: Response<UpdateData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        updateData = response.body()!!
                        getProvData()
                    }
                }
            }
        })
    }

    private fun getProvData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_GOV)

        val call = apiRequest.getProvData()
        call.enqueue(object : Callback<ProvData> {
            override fun onFailure(call: Call<ProvData>, t: Throwable) {
                containerTimeoutDashboard.visibility = View.VISIBLE
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
            }

            override fun onResponse(call: Call<ProvData>, response: Response<ProvData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        provData = response.body()!!
                        getWorldDataSummary()
                    }
                }
            }
        })
    }

    private fun getWorldDataSummary() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_MATHDROID)

        val call = apiRequest.getWorldSummaryData()
        call.enqueue(object : Callback<GlobalCasesSummary>{
            override fun onFailure(call: Call<GlobalCasesSummary>, t: Throwable) {
                containerTimeoutDashboard.visibility = View.VISIBLE
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
            }

            override fun onResponse(
                call: Call<GlobalCasesSummary>,
                response: Response<GlobalCasesSummary>
            ) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        globalPositif = response.body()!!.confirmed.value
                        globalRecovered = response.body()!!.recovered.value
                        globalDeath = response.body()!!.deaths.value
                        setData()
                    }
                }
            }
        })
    }

    /*
    private fun getSummaryIndoData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient)

        val call = apiRequest.getSummaryIndoData()
        call.enqueue(object : Callback<List<SummaryIndoDataItem>>{
            override fun onFailure(call: Call<List<SummaryIndoDataItem>>, t: Throwable) {
                containerTimeoutDashboard.visibility = View.VISIBLE
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
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
                containerTimeoutDashboard.visibility = View.VISIBLE
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
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
                containerTimeoutDashboard.visibility = View.VISIBLE
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
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
                containerTimeoutDashboard.visibility = View.VISIBLE
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
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
                containerTimeoutDashboard.visibility = View.VISIBLE
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
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
    }*/

    private fun setData(){
        var provinsiPositifInt = 0
        var provinsiRecoveredInt = 0
        var provinsiDeathInt = 0

        val positifIndo : Int = updateData.update.total.jumlahPositif
        val recoveredIndo : Int = updateData.update.total.jumlahSembuh
        val deathIndo : Int = updateData.update.total.jumlahMeninggal

        tvPositifCountryDashboard.text = NumberFormat.getInstance(Locale.getDefault()).
            format(positifIndo)
        tvRecoveredCountryDashboard.text = NumberFormat.getInstance(Locale.getDefault())
            .format(recoveredIndo)
        tvDeathCountryDashboard.text = NumberFormat.getInstance(Locale.getDefault())
            .format(deathIndo)

        for (i in provData.listData.indices){
            val provinsiItem = provData.listData[i]
            if(provinsiItem.key == "JAWA TIMUR"){
                provinsiPositifInt = provinsiItem.jumlahKasus
                provinsiDeathInt = provinsiItem.jumlahMeninggal
                provinsiRecoveredInt = provinsiItem.jumlahSembuh
                break
            }
        }

        val persenPositif = (provinsiPositifInt.toDouble() / positifIndo) * 100
        val persenRecovered = (provinsiRecoveredInt.toDouble() / recoveredIndo) * 100
        val persenDeath = (provinsiDeathInt.toDouble() / deathIndo) * 100

        tvPersenPositifProvinceDashboard.text = "(${persenPositif.toInt()}%)"
        tvPersenRecoveredProvinceDashboard.text = "(${persenRecovered.toInt()}%)"
        tvPersenDeathProvinceDashboard.text = "(${persenDeath.toInt()}%)"

        tvPositifProvinceDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(provinsiPositifInt)
        tvRecoveredProvinceDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(provinsiRecoveredInt)
        tvDeathProvinceDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(provinsiDeathInt)

        tvPositifGlobalDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(globalPositif)
        tvRecoveredGlobalDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(globalRecovered)
        tvDeathGlobalDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(globalDeath)

        containerDataDashboard.visibility = View.VISIBLE
        containerShimmerDashboard.visibility = View.GONE
        containerShimmerDashboard.stopShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
