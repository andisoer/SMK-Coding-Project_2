package com.soerjdev.smkcodingproject2.database.repository

import androidx.lifecycle.LiveData
import com.soerjdev.smkcodingproject2.database.dao.GlobalSummaryDao
import com.soerjdev.smkcodingproject2.database.model.GlobalSummary

class GlobalSummaryRepository(private val globalSummaryDao: GlobalSummaryDao) {

    val globalSummaryData: LiveData<GlobalSummary> = globalSummaryDao.getGlobalSummary()

    suspend fun insert(globalSummary: GlobalSummary){
        globalSummaryDao.insert(globalSummary)
    }

    suspend fun deleteAll(){
        globalSummaryDao.deleteAll()
    }

}