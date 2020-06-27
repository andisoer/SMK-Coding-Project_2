package com.soerjdev.smkcodingproject2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soerjdev.smkcodingproject2.database.AppLocalDatabase
import com.soerjdev.smkcodingproject2.database.model.ProvinsiCases
import com.soerjdev.smkcodingproject2.database.repository.ProvinsiCasesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProvinsiCasesViewModel(application: Application) : AndroidViewModel(application){

    private val repository: ProvinsiCasesRepository

    val allProvinsiCases: LiveData<List<ProvinsiCases>>

    init {
        val provinsiCasesDao = AppLocalDatabase.getDatabase(application).provinsiCasesDao()
        repository = ProvinsiCasesRepository(provinsiCasesDao)
        allProvinsiCases = repository.allProvinsiCase
    }

    fun insert(provinsiCases: List<ProvinsiCases>) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
        repository.insert(provinsiCases)
    }


}