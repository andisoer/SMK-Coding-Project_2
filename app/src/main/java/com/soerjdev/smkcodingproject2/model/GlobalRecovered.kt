package com.soerjdev.smkcodingproject2.model


import com.google.gson.annotations.SerializedName

data class GlobalRecovered(
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)