package com.soerjdev.smkcodingproject2.database.repository

import androidx.lifecycle.LiveData
import com.soerjdev.smkcodingproject2.database.dao.ProvinsiCasesDao
import com.soerjdev.smkcodingproject2.database.model.ProvinsiCases

class ProvinsiCasesRepository(private val provinsiCasesDao: ProvinsiCasesDao) {

    val allProvinsiCase: LiveData<List<ProvinsiCases>> = provinsiCasesDao.getProvinsiCases()

    suspend fun insert(provinsiCases: List<ProvinsiCases>){
        provinsiCasesDao.insert(provinsiCases)
    }

    suspend fun deleteAll(){
        provinsiCasesDao.deleteAll()
    }

}