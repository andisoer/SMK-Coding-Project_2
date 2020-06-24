package com.soerjdev.smkcodingproject2.model.globalcasesummary


import com.google.gson.annotations.SerializedName

data class DailyTimeSeries(
    @SerializedName("example")
    val example: String,
    @SerializedName("pattern")
    val pattern: String
)