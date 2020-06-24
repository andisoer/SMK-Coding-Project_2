package com.soerjdev.smkcodingproject2.model.provdata


import com.google.gson.annotations.SerializedName
import com.soerjdev.smkcodingproject2.model.provdata.data.JenisKelamin
import com.soerjdev.smkcodingproject2.model.provdata.data.KelompokUmur
import com.soerjdev.smkcodingproject2.model.provdata.data.Lokasi

data class Data(
    @SerializedName("doc_count")
    val docCount: Double,
    @SerializedName("jenis_kelamin")
    val jenisKelamin: List<JenisKelamin>,
    @SerializedName("jumlah_dirawat")
    val jumlahDirawat: Int,
    @SerializedName("jumlah_kasus")
    val jumlahKasus: Int,
    @SerializedName("jumlah_meninggal")
    val jumlahMeninggal: Int,
    @SerializedName("jumlah_sembuh")
    val jumlahSembuh: Int,
    @SerializedName("kelompok_umur")
    val kelompokUmur: List<KelompokUmur>,
    @SerializedName("key")
    val key: String,
    @SerializedName("lokasi")
    val lokasi: Lokasi
)