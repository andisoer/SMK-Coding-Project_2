package com.soerjdev.smkcodingproject2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soerjdev.smkcodingproject2.database.AppLocalDatabase
import com.soerjdev.smkcodingproject2.database.model.GlobalCases
import com.soerjdev.smkcodingproject2.database.repository.GlobalCasesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GlobalCasesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GlobalCasesRepository

    val allGlobalCases: LiveData<List<GlobalCases>>

    init {
        val globalCasesDao = AppLocalDatabase.getDatabase(application).globalCasesDa()
        repository = GlobalCasesRepository(globalCasesDao)
        allGlobalCases = repository.allGlobalCases
    }

    fun insert(globalCases: List<GlobalCases>) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
        repository.insert(globalCases)
    }

}