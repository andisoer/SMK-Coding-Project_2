package com.soerjdev.smkcodingproject2.model.globalcasesummary


import com.google.gson.annotations.SerializedName

data class GlobalCasesSummary(
    @SerializedName("confirmed")
    val confirmed: Confirmed,
    @SerializedName("countries")
    val countries: String,
    @SerializedName("countryDetail")
    val countryDetail: CountryDetail,
    @SerializedName("dailySummary")
    val dailySummary: String,
    @SerializedName("dailyTimeSeries")
    val dailyTimeSeries: DailyTimeSeries,
    @SerializedName("deaths")
    val deaths: Deaths,
    @SerializedName("image")
    val image: String,
    @SerializedName("lastUpdate")
    val lastUpdate: String,
    @SerializedName("recovered")
    val recovered: Recovered,
    @SerializedName("source")
    val source: String
)