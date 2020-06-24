package com.soerjdev.smkcodingproject2.model.globalcasesummary


import com.google.gson.annotations.SerializedName

data class Confirmed(
    @SerializedName("detail")
    val detail: String,
    @SerializedName("value")
    val value: Int
)