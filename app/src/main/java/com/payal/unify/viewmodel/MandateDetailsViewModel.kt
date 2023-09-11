package com.payal.unify.viewmodel

import androidx.lifecycle.ViewModel
import com.payal.unify.model.ApiResponse
import com.payal.unify.repository.MockRepository

class MandateDetailsViewModel : ViewModel() {
    private val repository = MockRepository()

    fun getApiResponse(): ApiResponse {
        return repository.getApiResponse()
    }
}