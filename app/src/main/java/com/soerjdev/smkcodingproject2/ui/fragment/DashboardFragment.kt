package com.soerjdev.smkcodingproject2.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.api.ApiEndPoints
import com.soerjdev.smkcodingproject2.api.apiRequest
import com.soerjdev.smkcodingproject2.api.httpClient
import com.soerjdev.smkcodingproject2.database.model.GlobalSummary
import com.soerjdev.smkcodingproject2.database.model.IndoSummary
import com.soerjdev.smkcodingproject2.database.model.ProvinsiCases
import com.soerjdev.smkcodingproject2.model.globalcasesummary.GlobalCasesSummary
import com.soerjdev.smkcodingproject2.model.provdata.ProvData
import com.soerjdev.smkcodingproject2.model.updatedata.UpdateData
import com.soerjdev.smkcodingproject2.ui.AllCountryDataActivity
import com.soerjdev.smkcodingproject2.ui.AllProvinceDataActivity
import com.soerjdev.smkcodingproject2.ui.PlaceHistoryListActivity
import com.soerjdev.smkcodingproject2.utils.ApiUtils
import com.soerjdev.smkcodingproject2.viewmodel.GlobalSummaryViewModel
import com.soerjdev.smkcodingproject2.viewmodel.IndoSummaryViewModel
import com.soerjdev.smkcodingproject2.viewmodel.ProvinsiCasesViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

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

    private lateinit var indoSummaryViewModel: IndoSummaryViewModel
    private lateinit var globalSummaryViewModel: GlobalSummaryViewModel
    private lateinit var provinsiViewModel: ProvinsiCasesViewModel
    private lateinit var indoSummaryData: IndoSummary
    private lateinit var globalSummaryData: GlobalSummary
    private var listProvinsiCase = emptyList<ProvinsiCases>()

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

        indoSummaryViewModel = ViewModelProvider(this).get(IndoSummaryViewModel::class.java)
        globalSummaryViewModel = ViewModelProvider(this).get(GlobalSummaryViewModel::class.java)
        provinsiViewModel = ViewModelProvider(this).get(ProvinsiCasesViewModel::class.java)


        containerNameProvinces.setOnClickListener {
            startActivity(Intent(context, AllProvinceDataActivity::class.java))
        }

        containerNameWorld.setOnClickListener {
            startActivity(Intent(context, AllCountryDataActivity::class.java))
        }

        containerPlaceHistoryDashboard.setOnClickListener {
            startActivity(Intent(context, PlaceHistoryListActivity::class.java))
        }

        indoSummaryViewModel.indoSummaryData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { indoSummary ->
            indoSummary?.let {
                if(it != null){
                    indoSummaryData = it
                    if(listProvinsiCase.isNotEmpty()){
                        setIndoData()
                    }
                }
            }
        })

        provinsiViewModel.allProvinsiCases.observe(viewLifecycleOwner, androidx.lifecycle.Observer { provinceCase ->
            provinceCase?.let {
                if(it.isNotEmpty()){
                    listProvinsiCase = it
                    setIndoData()
                }
            }
        })

        globalSummaryViewModel.globalSummaryData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { globalSummary ->
            globalSummary?.let {
                if(it != null){
                    globalSummaryData = it
                    setData()
                }
            }
        })

        getUpdateData()
    }

    private fun setIndoData() {

        var provinsiPositifInt = 0
        var provinsiRecoveredInt = 0
        var provinsiDeathInt = 0

        val positifIndo : Int = indoSummaryData.jumlah_positif
        val recoveredIndo : Int = indoSummaryData.jumlah_sembuh
        val deathIndo : Int = indoSummaryData.jumlah_meninggal

        tvPositifCountryDashboard.text = NumberFormat.getInstance(Locale.getDefault()).
        format(positifIndo)
        tvRecoveredCountryDashboard.text = NumberFormat.getInstance(Locale.getDefault())
            .format(recoveredIndo)
        tvDeathCountryDashboard.text = NumberFormat.getInstance(Locale.getDefault())
            .format(deathIndo)

        val persenPositif = (provinsiPositifInt.toDouble() / positifIndo) * 100
        val persenRecovered = (provinsiRecoveredInt.toDouble() / recoveredIndo) * 100
        val persenDeath = (provinsiDeathInt.toDouble() / deathIndo) * 100

        for (provinsiCases in listProvinsiCase){
            if(provinsiCases.key == "JAWA TIMUR"){
                provinsiPositifInt = provinsiCases.jumlahKasus
                provinsiDeathInt = provinsiCases.jumlahMeninggal
                provinsiRecoveredInt = provinsiCases.jumlahSembuh
                break
            }
        }

        tvPersenPositifProvinceDashboard.text = "(${persenPositif.toInt()}%)"
        tvPersenRecoveredProvinceDashboard.text = "(${persenRecovered.toInt()}%)"
        tvPersenDeathProvinceDashboard.text = "(${persenDeath.toInt()}%)"

        tvPositifProvinceDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(provinsiPositifInt)
        tvRecoveredProvinceDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(provinsiRecoveredInt)
        tvDeathProvinceDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(provinsiDeathInt)

    }

    private fun getUpdateData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_GOV)

        val call = apiRequest.getUpdateData()

        call.enqueue(object: Callback<UpdateData> {
            override fun onFailure(call: Call<UpdateData>, t: Throwable) {
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
                containerDataDashboard.visibility = View.VISIBLE

            }

            override fun onResponse(call: Call<UpdateData>, response: Response<UpdateData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        updateData = response.body()!!
                        val indoSummaryDatas = IndoSummary(
                            updateData.update.total.jumlahDirawat,
                            updateData.update.total.jumlahMeninggal,
                            updateData.update.total.jumlahPositif,
                            updateData.update.total.jumlahSembuh)

                        indoSummaryViewModel.insert(indoSummaryDatas)
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
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
                containerDataDashboard.visibility = View.VISIBLE

            }

            override fun onResponse(call: Call<ProvData>, response: Response<ProvData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        provData = response.body()!!
                        val provinceCasesList: ArrayList<ProvinsiCases> = ArrayList()

                        for (data in provData.listData){
                            val provinsiCases = ProvinsiCases(
                                data.docCount,
                                data.jumlahDirawat,
                                data.jumlahKasus,
                                data.jumlahMeninggal,
                                data.jumlahSembuh,
                                data.key)

                            provinceCasesList.add(provinsiCases)
                        }
                        provinsiViewModel.insert(provinceCasesList)
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
                containerShimmerDashboard.visibility = View.GONE
                containerShimmerDashboard.stopShimmer()
                containerDataDashboard.visibility = View.VISIBLE

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

                        globalSummaryData = GlobalSummary(response.body()!!.confirmed.value, response.body()!!.deaths.value, response.body()!!.recovered.value)
                        globalSummaryViewModel.insert(globalSummaryData)

                        containerDataDashboard.visibility = View.VISIBLE
                        containerShimmerDashboard.visibility = View.GONE
                        containerShimmerDashboard.stopShimmer()
                    }
                }
            }
        })
    }

    private fun setData(){
        tvPositifGlobalDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(globalSummaryData.confirmed)
        tvRecoveredGlobalDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(globalSummaryData.recovered)
        tvDeathGlobalDashboard.text = NumberFormat.getInstance(Locale.getDefault()).format(globalSummaryData.deaths)

    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
