package com.soerjdev.smkcodingproject2.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "provinsi_cases")
class ProvinsiCases(
    @ColumnInfo(name = "doc_count") val docCount: Double,
    @ColumnInfo(name = "jumlah_dirawat") val jumlahDirawat: Int,
    @ColumnInfo(name = "jumlah_kasus") val jumlahKasus: Int,
    @ColumnInfo(name = "jumlah_meninggal") val jumlahMeninggal: Int,
    @ColumnInfo(name = "jumlah_sembuh") val jumlahSembuh: Int,
    @PrimaryKey @ColumnInfo(name = "key") val key: String
)