package com.soerjdev.smkcodingproject2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.soerjdev.smkcodingproject2.adapter.AllCountryListAdapter
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.api.ApiEndPoints
import com.soerjdev.smkcodingproject2.api.apiRequest
import com.soerjdev.smkcodingproject2.api.httpClient
import com.soerjdev.smkcodingproject2.model.globaldata.GlobalDataItem
import com.soerjdev.smkcodingproject2.utils.ApiUtils
import kotlinx.android.synthetic.main.activity_all_country_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCountryDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_country_data)

        initView()
    }

    private fun initView() {
        setSupportActionBar(tbAllCountryData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getAllCountryData()
    }

    private fun setData(listData: List<GlobalDataItem>) {
        rvAllCountryData.layoutManager = LinearLayoutManager(this)
        rvAllCountryData.adapter =
            AllCountryListAdapter(
                this,
                listData
            )
        pbLoadDataCountry.visibility = View.GONE
    }

    private fun getAllCountryData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_GOV)

        val call = apiRequest.getGlobalListData()
        call.enqueue(object : Callback<List<GlobalDataItem>> {
            override fun onFailure(call: Call<List<GlobalDataItem>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<GlobalDataItem>>,
                response: Response<List<GlobalDataItem>>
            ) {
                when {
                    response.isSuccessful ->
                        when {
                            response.body()?.size != 0 ->
                                setData(response.body()!!)
                        }
                }
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()

        return true
    }
}
