package com.soerjdev.smkcodingproject2.model.provinsidata


import com.google.gson.annotations.SerializedName
import com.soerjdev.smkcodingproject2.model.provinsidata.Attributes

data class ProvinsiDataItem(
    @SerializedName("attributes")
    val attributes: Attributes
)