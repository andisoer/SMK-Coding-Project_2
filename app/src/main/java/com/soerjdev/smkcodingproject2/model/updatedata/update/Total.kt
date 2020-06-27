package com.soerjdev.smkcodingproject2.model.updatedata.update


import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "total_indo_data")
data class Total(
    @SerializedName("jumlah_dirawat")
    @ColumnInfo(name = "jumlah_dirawat")
    val jumlahDirawat: Int,

    @SerializedName("jumlah_meninggal")
    @ColumnInfo(name = "jumlah_meninggal")
    val jumlahMeninggal: Int,

    @SerializedName("jumlah_positif")
    @ColumnInfo(name = "jumlah_positif")
    val jumlahPositif: Int,

    @SerializedName("jumlah_sembuh")
    @ColumnInfo(name = "jumlah_sembuh")
    val jumlahSembuh: Int
)