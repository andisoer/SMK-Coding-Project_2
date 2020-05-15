package com.soerjdev.smkcodingproject2.model.summaryindodata


import com.google.gson.annotations.SerializedName

data class SummaryIndoDataItem(
    @SerializedName("dirawat")
    val dirawat: String,
    @SerializedName("meninggal")
    val meninggal: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("positif")
    val positif: String,
    @SerializedName("sembuh")
    val sembuh: String
)