package com.soerjdev.smkcodingproject2.model.updatedata.update


import com.google.gson.annotations.SerializedName
import com.soerjdev.smkcodingproject2.model.updatedata.update.Harian
import com.soerjdev.smkcodingproject2.model.updatedata.update.Penambahan
import com.soerjdev.smkcodingproject2.model.updatedata.update.Total

data class Update(
    @SerializedName("harian")
    val harian: List<Harian>,
    @SerializedName("penambahan")
    val penambahan: Penambahan,
    @SerializedName("total")
    val total: Total
)