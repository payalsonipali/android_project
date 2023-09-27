package com.payal.evaluateexpressions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.payal.evaluateexpressions.repository.MathApiRepository

class MathApiViewModelFactory(
    private val workManager:WorkManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MathApiViewModel::class.java)) {
            return MathApiViewModel(workManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}