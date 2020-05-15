package com.soerjdev.smkcodingproject2.api

import com.soerjdev.smkcodingproject2.model.globaldata.GlobalDataItem
import com.soerjdev.smkcodingproject2.model.provinsidata.ProvinsiDataItem
import com.soerjdev.smkcodingproject2.model.summaryindodata.SummaryIndoDataItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndPoints {

    @GET(".")
    fun getGlobalListData(): Call<List<GlobalDataItem>>

    @GET("indonesia/provinsi/")
    fun getProvinceListData(): Call<List<ProvinsiDataItem>>

    @GET("indonesia")
    fun getSummaryIndoData(): Call<List<SummaryIndoDataItem>>
}