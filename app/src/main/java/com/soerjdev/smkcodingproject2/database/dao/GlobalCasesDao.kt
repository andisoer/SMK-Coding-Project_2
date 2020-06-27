package com.soerjdev.smkcodingproject2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soerjdev.smkcodingproject2.database.model.GlobalCases

@Dao
interface GlobalCasesDao {
    @Query("SELECT * FROM global_cases ORDER BY confirmed DESC")
    fun getGlobalCases(): LiveData<List<GlobalCases>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(globalCases: List<GlobalCases>)

    @Query("DELETE FROM global_cases")
    suspend fun deleteAll()
}