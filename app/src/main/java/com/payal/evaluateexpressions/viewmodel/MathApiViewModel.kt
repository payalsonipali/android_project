package com.payal.evaluateexpressions.viewmodel

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.payal.evaluateexpressions.EvaluateExpressionWorker

class MathApiViewModel(
    private val workManager: WorkManager
) : ViewModel() {
    private lateinit var evaluateExpressionWorkRequest: OneTimeWorkRequest

    private val _results = MutableLiveData<String>()
    val results: LiveData<String> get() = _results

    private val _errorExpressions = MutableLiveData<List<String>>(emptyList())
    val errorExpressions: LiveData<List<String>> get() = _errorExpressions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val workerResultLiveData = MutableLiveData<String>()

    fun onEvaluateButtonClick(expressionsText: String) {
        _isLoading.value = true
        val expressionsArray = expressionsText.split("\n")
        val expressionsList = ArrayList<String>()

        for (expression in expressionsArray) {
            val trimmedExpression = expression.trim()
            if (trimmedExpression.isNotEmpty()) {
                expressionsList.add(trimmedExpression)
            }
        }

        // Enqueue the work to evaluate expressions in the background
        val inputData = Data.Builder()
            .putStringArray("expressions", expressionsList.toTypedArray())
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        evaluateExpressionWorkRequest =
            OneTimeWorkRequestBuilder<EvaluateExpressionWorker>()
                .setConstraints(constraints)
                .setInputData(inputData)
                .build()

        workManager.enqueue(evaluateExpressionWorkRequest)
        workManager.getWorkInfoByIdLiveData(evaluateExpressionWorkRequest.id)
            .observeForever { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    // Get the result from the worker's LiveData and update _results
                    _isLoading.value = false
                    val result = workInfo.outputData.getString("results")
                    val errors = workInfo.outputData.getString("errors")
                    _results.postValue(result ?: "")

                    val errorList = errors?.split("\n")
                    _errorExpressions.value = errorList ?: emptyList()
                    workerResultLiveData.postValue(result ?: "")
                }
            }
    }

    fun showSnackBar(view: View, error: String, context: Context) {
        val snackbar = Snackbar.make(view, error, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(context, android.R.color.white))
        snackbar.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
        snackbar.show()
    }
}
