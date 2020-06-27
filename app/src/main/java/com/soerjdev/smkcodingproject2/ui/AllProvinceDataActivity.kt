package com.soerjdev.smkcodingproject2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.soerjdev.smkcodingproject2.adapter.ProvinsiListAdapter
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.api.ApiEndPoints
import com.soerjdev.smkcodingproject2.api.apiRequest
import com.soerjdev.smkcodingproject2.api.httpClient
import com.soerjdev.smkcodingproject2.database.model.ProvinsiCases
import com.soerjdev.smkcodingproject2.model.provdata.ProvData
import com.soerjdev.smkcodingproject2.utils.ApiUtils
import com.soerjdev.smkcodingproject2.viewmodel.ProvinsiCasesViewModel
import kotlinx.android.synthetic.main.activity_all_province_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllProvinceDataActivity : AppCompatActivity() {

    private lateinit var provinsiCasesViewModel: ProvinsiCasesViewModel
    private val adapter = ProvinsiListAdapter(this)

    companion object {
        val TAG = AllProvinceDataActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_province_data)

        initView()
    }

    private fun initView() {
        setSupportActionBar(tbAllProvinceData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvAllProvinceData.layoutManager = LinearLayoutManager(this)

        provinsiCasesViewModel = ViewModelProvider(this).get(ProvinsiCasesViewModel::class.java)

        rvAllProvinceData.adapter = adapter

        provinsiCasesViewModel.allProvinsiCases.observe(this, Observer { provinsiCases ->
            provinsiCases?.let {
                Log.d(TAG, "data size : "+it.size)
                if(it.isNotEmpty()){
                    adapter.setProvinsiCases(it)
                    pbLoadDataProvince.visibility = View.GONE

                }
            }
        })

        getAllProvinceData()
    }

    private fun setData(provData: ProvData) {

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

        provinsiCasesViewModel.insert(provinceCasesList)

//        rvAllProvinceData.layoutManager = LinearLayoutManager(this)
//        rvAllProvinceData.adapter =
//            ProvinsiListAdapter(
//                this,
//                listData
//            )
//
    }

    private fun getAllProvinceData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_GOV)

        val call = apiRequest.getProvData()
        call.enqueue(object : Callback<ProvData> {
            override fun onFailure(call: Call<ProvData>, t: Throwable) {
            }

            override fun onResponse(call: Call<ProvData>, response: Response<ProvData>) {
                when {
                    response.isSuccessful ->
                        when {
                            response.body() != null ->
                                setData(response.body()!!)
                        }
                }
            }
        })

//        val call = apiRequest.getProvinceListData()
//        call.enqueue(object : Callback<List<ProvinsiDataItem>> {
//            override fun onFailure(call: Call<List<ProvinsiDataItem>>, t: Throwable) {
//
//            }
//
//            override fun onResponse(
//                call: Call<List<ProvinsiDataItem>>,
//                response: Response<List<ProvinsiDataItem>>
//            ) {
//                when {
//                    response.isSuccessful ->
//                        when {
//                            response.body()?.size != 0 ->
//                                setData(response.body()!!)
//                        }
//                }
//            }
//
//        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()

        return true
    }
}
