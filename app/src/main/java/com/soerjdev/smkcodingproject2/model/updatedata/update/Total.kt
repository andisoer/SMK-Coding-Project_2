package com.soerjdev.smkcodingproject2.model.updatedata.update


import com.google.gson.annotations.SerializedName

data class Total(
    @SerializedName("jumlah_dirawat")
    val jumlahDirawat: Int,
    @SerializedName("jumlah_meninggal")
    val jumlahMeninggal: Int,
    @SerializedName("jumlah_positif")
    val jumlahPositif: Int,
    @SerializedName("jumlah_sembuh")
    val jumlahSembuh: Int
)