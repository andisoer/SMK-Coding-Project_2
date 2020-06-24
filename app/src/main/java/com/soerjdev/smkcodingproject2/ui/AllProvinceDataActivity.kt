package com.soerjdev.smkcodingproject2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.soerjdev.smkcodingproject2.adapter.ProvinsiListAdapter
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.api.ApiEndPoints
import com.soerjdev.smkcodingproject2.api.apiRequest
import com.soerjdev.smkcodingproject2.api.httpClient
import com.soerjdev.smkcodingproject2.model.provdata.ProvData
import com.soerjdev.smkcodingproject2.model.provinsidata.ProvinsiDataItem
import com.soerjdev.smkcodingproject2.utils.ApiUtils
import kotlinx.android.synthetic.main.activity_all_province_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllProvinceDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_province_data)

        initView()
    }

    private fun initView() {
        setSupportActionBar(tbAllProvinceData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getAllProvinceData()
    }

    private fun setData(provData: ProvData) {

        rvAllProvinceData.layoutManager = LinearLayoutManager(this)
        rvAllProvinceData.adapter = ProvinsiListAdapter(this, provData.listData)

        pbLoadDataProvince.visibility = View.GONE
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
