package com.soerjdev.smkcodingproject2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soerjdev.smkcodingproject2.database.model.GlobalSummary

@Dao
interface GlobalSummaryDao {

    @Query("SELECT * FROM global_summary")
    fun getGlobalSummary(): LiveData<GlobalSummary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(globalSummary: GlobalSummary)

    @Query("DELETE FROM global_summary")
    suspend fun deleteAll()
}