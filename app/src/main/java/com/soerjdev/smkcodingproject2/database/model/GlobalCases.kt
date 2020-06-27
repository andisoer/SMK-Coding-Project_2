package com.soerjdev.smkcodingproject2.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "global_cases")
class GlobalCases (
    @ColumnInfo(name = "combinedKey") val combinedKey: String,
    @ColumnInfo(name = "confirmed") val confirmed: Int,
    @ColumnInfo(name = "deaths") val deaths: Int,
    @ColumnInfo(name = "recovered") val recovered: Int,
    @PrimaryKey @ColumnInfo(name = "uid") val uid: Int
)