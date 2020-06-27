package com.soerjdev.smkcodingproject2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soerjdev.smkcodingproject2.database.AppLocalDatabase
import com.soerjdev.smkcodingproject2.database.model.GlobalSummary
import com.soerjdev.smkcodingproject2.database.repository.GlobalSummaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GlobalSummaryViewModel(application: Application): AndroidViewModel(application){

    private val repository: GlobalSummaryRepository

    val globalSummaryData: LiveData<GlobalSummary>

    init {
        val globalSummaryDao = AppLocalDatabase.getDatabase(application).globalSummaryDao()
        repository = GlobalSummaryRepository(globalSummaryDao)
        globalSummaryData = repository.globalSummaryData
    }

    fun insert(globalSummary: GlobalSummary) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
        repository.insert(globalSummary)
    }

}