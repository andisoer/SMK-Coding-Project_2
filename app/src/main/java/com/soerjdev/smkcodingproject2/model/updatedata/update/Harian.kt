package com.soerjdev.smkcodingproject2.model.updatedata.update


import com.google.gson.annotations.SerializedName
import com.soerjdev.smkcodingproject2.model.updatedata.update.harian.*

data class Harian(
    @SerializedName("doc_count")
    val docCount: Int,
    @SerializedName("jumlah_dirawat")
    val jumlahDirawat: JumlahDirawat,
    @SerializedName("jumlah_dirawat_kum")
    val jumlahDirawatKum: JumlahDirawatKum,
    @SerializedName("jumlah_meninggal")
    val jumlahMeninggal: JumlahMeninggal,
    @SerializedName("jumlah_meninggal_kum")
    val jumlahMeninggalKum: JumlahMeninggalKum,
    @SerializedName("jumlah_positif")
    val jumlahPositif: JumlahPositif,
    @SerializedName("jumlah_positif_kum")
    val jumlahPositifKum: JumlahPositifKum,
    @SerializedName("jumlah_sembuh")
    val jumlahSembuh: JumlahSembuh,
    @SerializedName("jumlah_sembuh_kum")
    val jumlahSembuhKum: JumlahSembuhKum,
    @SerializedName("key")
    val key: Long,
    @SerializedName("key_as_string")
    val keyAsString: String
)