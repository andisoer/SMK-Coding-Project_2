package com.soerjdev.smkcodingproject2.model.globaldata


import com.google.gson.annotations.SerializedName
import com.soerjdev.smkcodingproject2.model.globaldata.Attributes

data class GlobalDataItem(
    @SerializedName("attributes")
    val attributes: Attributes
)