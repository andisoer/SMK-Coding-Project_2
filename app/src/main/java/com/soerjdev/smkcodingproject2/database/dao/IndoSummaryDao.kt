package com.soerjdev.smkcodingproject2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soerjdev.smkcodingproject2.database.model.IndoSummary

@Dao
interface IndoSummaryDao{
    @Query("SELECT * FROM indo_summary")
    fun getIndoSummary(): LiveData<IndoSummary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(indoSummary: IndoSummary)

    @Query("DELETE FROM indo_summary")
    suspend fun deleteAll()
}