package com.soerjdev.smkcodingproject2.model.updatedata


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("jumlah_odp")
    val jumlahOdp: Int,
    @SerializedName("jumlah_pdp")
    val jumlahPdp: Int,
    @SerializedName("total_spesimen")
    val totalSpesimen: Int,
    @SerializedName("total_spesimen_negatif")
    val totalSpesimenNegatif: Int
)