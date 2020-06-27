package com.soerjdev.smkcodingproject2.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "global_summary")
class GlobalSummary(
    @PrimaryKey @ColumnInfo(name = "confirmed") val confirmed: Int,
    @ColumnInfo(name = "deaths") val deaths: Int,
    @ColumnInfo(name = "recovered") val recovered: Int
)