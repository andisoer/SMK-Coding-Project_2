package com.soerjdev.smkcodingproject2.model


import com.google.gson.annotations.SerializedName

data class GlobalPositif(
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)