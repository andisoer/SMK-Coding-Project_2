package com.soerjdev.smkcodingproject2.api

import com.soerjdev.smkcodingproject2.model.globalcases.GlobalCasesItem
import com.soerjdev.smkcodingproject2.model.globalcasesummary.GlobalCasesSummary
import com.soerjdev.smkcodingproject2.model.provdata.ProvData
import com.soerjdev.smkcodingproject2.model.updatedata.UpdateData
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndPoints {

    @GET("update.json")
    fun getUpdateData(): Call<UpdateData>

    @GET("prov.json")
    fun getProvData(): Call<ProvData>

    @GET("confirmed")
    fun getWorldConfirmedData(): Call<List<GlobalCasesItem>>

    @GET("recovered")
    fun getWorldRecoveredData(): Call<List<GlobalCasesItem>>

    @GET("deaths")
    fun getWorldDeathData(): Call<List<GlobalCasesItem>>

    @GET(".")
    fun getWorldSummaryData(): Call<GlobalCasesSummary>
}