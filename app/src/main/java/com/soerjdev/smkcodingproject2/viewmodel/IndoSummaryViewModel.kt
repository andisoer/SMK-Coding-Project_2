package com.soerjdev.smkcodingproject2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soerjdev.smkcodingproject2.database.AppLocalDatabase
import com.soerjdev.smkcodingproject2.database.model.GlobalSummary
import com.soerjdev.smkcodingproject2.database.model.IndoSummary
import com.soerjdev.smkcodingproject2.database.repository.IndoSummaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IndoSummaryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IndoSummaryRepository

    val indoSummaryData: LiveData<IndoSummary>

    init {
        val indoSummaryDao = AppLocalDatabase.getDatabase(application).indoSummaryDao()
        repository = IndoSummaryRepository(indoSummaryDao)
        indoSummaryData = repository.indoSummaryData
    }

    fun insert(indoSummary: IndoSummary) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
        repository.insert(indoSummary)
    }

}