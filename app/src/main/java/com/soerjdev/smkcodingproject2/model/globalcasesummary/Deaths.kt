package com.soerjdev.smkcodingproject2.model.globalcasesummary


import com.google.gson.annotations.SerializedName

data class Deaths(
    @SerializedName("detail")
    val detail: String,
    @SerializedName("value")
    val value: Int
)