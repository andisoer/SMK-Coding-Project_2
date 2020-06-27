package com.soerjdev.smkcodingproject2.database.repository

import androidx.lifecycle.LiveData
import com.soerjdev.smkcodingproject2.database.dao.GlobalCasesDao
import com.soerjdev.smkcodingproject2.database.model.GlobalCases

class GlobalCasesRepository(private val globalCasesDao: GlobalCasesDao) {

    val allGlobalCases: LiveData<List<GlobalCases>> = globalCasesDao.getGlobalCases()

    suspend fun insert(globalCases: List<GlobalCases>){
        globalCasesDao.insert(globalCases)
    }

    suspend fun deleteAll(){
        globalCasesDao.deleteAll()
    }
}