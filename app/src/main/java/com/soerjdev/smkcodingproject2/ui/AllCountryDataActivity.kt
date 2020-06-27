package com.soerjdev.smkcodingproject2.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.soerjdev.smkcodingproject2.adapter.AllCountryListAdapter
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.api.ApiEndPoints
import com.soerjdev.smkcodingproject2.api.apiRequest
import com.soerjdev.smkcodingproject2.api.httpClient
import com.soerjdev.smkcodingproject2.database.model.GlobalCases
import com.soerjdev.smkcodingproject2.model.globalcases.GlobalCasesItem
import com.soerjdev.smkcodingproject2.utils.ApiUtils
import com.soerjdev.smkcodingproject2.viewmodel.GlobalCasesViewModel
import kotlinx.android.synthetic.main.activity_all_country_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCountryDataActivity : AppCompatActivity(),
    Toolbar.OnMenuItemClickListener {

    /** ## Filter Description ##
     confirmed = get confirmed data @getWorldConfirmData
     recovered = get recovered data @getWorldRecoveredData
     death = get death data @getWorldDeathData
    */
    private var itemDialog = arrayOf("Positif","Sembuh","Meninggal")
    private var checkedItem : Int = 0
    private var tagFilter = "Positif"

    private lateinit var globalCasesViewModel: GlobalCasesViewModel
    private val adapter = AllCountryListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_country_data)

        initView()
    }

    private fun initView() {
        tbAllCountryData.setNavigationOnClickListener { finish() }
        tbAllCountryData.setOnMenuItemClickListener(this)

        rvAllCountryData.layoutManager = LinearLayoutManager(this)
        rvAllCountryData.adapter = adapter

        globalCasesViewModel = ViewModelProvider(this).get(GlobalCasesViewModel::class.java)

        globalCasesViewModel.allGlobalCases.observe(this, Observer { globalCases ->
            globalCases?.let {
                if(it.isNotEmpty()){
                    adapter.setGlobalCases(it)
                    pbLoadDataCountry.visibility = View.GONE
                }
            }
        })

        getAllCountryData()
    }

    private fun setData(listData: List<GlobalCasesItem>) {
        val globalCasesList: ArrayList<GlobalCases> = ArrayList()

        for (data in listData){
            val globalCases = GlobalCases(
                data.combinedKey,
                data.confirmed,
                data.deaths,
                data.recovered,
                data.uid
            )

            globalCasesList.add(globalCases)
        }

        globalCasesViewModel.insert(globalCasesList)
    }

    private fun getAllCountryData() {
        pbLoadDataCountry.visibility = View.VISIBLE

        val httpClient = httpClient()
        val apiRequest = apiRequest<ApiEndPoints>(httpClient, ApiUtils.URL_COVID_MATHDROID)

        when (tagFilter) {
            "Positif" -> {
                val call = apiRequest.getWorldConfirmedData()
                call.enqueue(object : Callback<List<GlobalCasesItem>> {
                    override fun onFailure(call: Call<List<GlobalCasesItem>>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<List<GlobalCasesItem>>,
                        response: Response<List<GlobalCasesItem>>
                    ) {
                        when {
                            response.isSuccessful ->
                                when {
                                    response.body() != null ->
                                        setData(response.body()!!)
                                }
                        }
                    }
                })
            }
            "Sembuh" -> {
                val call = apiRequest.getWorldRecoveredData()
                call.enqueue(object : Callback<List<GlobalCasesItem>> {
                    override fun onFailure(call: Call<List<GlobalCasesItem>>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<List<GlobalCasesItem>>,
                        response: Response<List<GlobalCasesItem>>
                    ) {
                        when {
                            response.isSuccessful ->
                                when {
                                    response.body() != null ->
                                        setData(response.body()!!)
                                }
                        }
                    }
                })
            }
            "Meninggal" -> {
                val call = apiRequest.getWorldDeathData()
                call.enqueue(object : Callback<List<GlobalCasesItem>> {
                    override fun onFailure(call: Call<List<GlobalCasesItem>>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<List<GlobalCasesItem>>,
                        response: Response<List<GlobalCasesItem>>
                    ) {
                        when {
                            response.isSuccessful ->
                                when {
                                    response.body() != null ->
                                        setData(response.body()!!)
                                }
                        }
                    }
                })
            }
        }
    }

    private fun showFilterDialog() {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
            .setTitle("Urutkan berdasarkan kasus")
            .setNeutralButton("Batal") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Pilih"){ dialogInterface, _ ->
                tagFilter = itemDialog[checkedItem]
                getAllCountryData()
                dialogInterface.dismiss()
            }
            .setSingleChoiceItems(itemDialog, checkedItem){ _ , i ->
                checkedItem = i
            }.show()

        materialAlertDialogBuilder.show()
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()

        return true
    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.menuFilterCases){
            showFilterDialog()
            return true
        }

        return false
    }
}
