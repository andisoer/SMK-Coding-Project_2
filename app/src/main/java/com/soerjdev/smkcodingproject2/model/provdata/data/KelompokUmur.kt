package com.soerjdev.smkcodingproject2.model.provdata.data


import com.google.gson.annotations.SerializedName

data class KelompokUmur(
    @SerializedName("doc_count")
    val docCount: Int,
    @SerializedName("key")
    val key: String,
    @SerializedName("usia")
    val usia: Usia
)