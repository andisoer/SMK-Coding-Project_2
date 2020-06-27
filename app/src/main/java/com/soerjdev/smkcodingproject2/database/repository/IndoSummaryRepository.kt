package com.soerjdev.smkcodingproject2.database.repository

import androidx.lifecycle.LiveData
import com.soerjdev.smkcodingproject2.database.dao.IndoSummaryDao
import com.soerjdev.smkcodingproject2.database.model.IndoSummary

class IndoSummaryRepository(private val indoSummaryDao: IndoSummaryDao) {

    val indoSummaryData: LiveData<IndoSummary> = indoSummaryDao.getIndoSummary()

    suspend fun insert(indoSummary: IndoSummary){
        indoSummaryDao.insert(indoSummary)
    }

    suspend fun deleteAll(){
        indoSummaryDao.deleteAll()
    }
}