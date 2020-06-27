package com.soerjdev.smkcodingproject2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soerjdev.smkcodingproject2.database.model.ProvinsiCases

@Dao
interface ProvinsiCasesDao  {
    @Query("SELECT * FROM provinsi_cases ORDER BY jumlah_kasus DESC")
    fun getProvinsiCases(): LiveData<List<ProvinsiCases>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(provinsiCases: List<ProvinsiCases>)

    @Query("DELETE FROM provinsi_cases")
    suspend fun deleteAll()
}