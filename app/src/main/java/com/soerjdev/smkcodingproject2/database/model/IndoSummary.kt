package com.soerjdev.smkcodingproject2.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "indo_summary")
class IndoSummary (
    @ColumnInfo(name = "jumlah_dirawat") val jumlah_dirawat: Int,
    @ColumnInfo(name = "jumlah_meninggal") val jumlah_meninggal: Int,
    @PrimaryKey @ColumnInfo(name = "jumlah_positif") val jumlah_positif: Int,
    @ColumnInfo(name = "jumlah_sembuh") val jumlah_sembuh: Int
)