package com.payal.evaluateexpressions

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.payal.evaluateexpressions.database.HistoryDatabase
import com.payal.evaluateexpressions.entity.HistoryItem
import com.payal.evaluateexpressions.repository.ApiResult
import com.payal.evaluateexpressions.repository.HistoryItemRepository
import com.payal.evaluateexpressions.repository.MathApiRepository
import com.payal.evaluateexpressions.viewmodel.HistoryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class EvaluateExpressionWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val applicationContext = applicationContext

        val mathApiRepository = MathApiRepository()
        val historyItemDao = HistoryDatabase.getInstance(applicationContext).historyItemDao()
        val historyRepository = HistoryItemRepository(historyItemDao)
        val historyViewModel = HistoryViewModel(historyRepository)

        val inputExpressions = inputData.getStringArray("expressions")?.toList() ?: emptyList()

        val resultsList = mutableListOf<String>()
        val errorList = mutableListOf<String>()

        val expressionsPerSecondLimit = 50
        val delayMillis = 1000L / expressionsPerSecondLimit

        for (expression in inputExpressions) {
            runBlocking {
                try {
                    val result: ApiResult<String>
                    result = mathApiRepository.evaluateExpression(expression)

                    when (result) {
                        is ApiResult.Success -> {
                            resultsList.add("$expression => ${result.data}")
                            val historyItem = HistoryItem(
                                expression = expression,
                                result = result.data,
                                submissionDate = System.currentTimeMillis()
                            )
                            historyRepository.insertHistoryItem(historyItem)
                        }

                        is ApiResult.Error -> {
                            errorList.add(result.message)
                        }
                    }
                } catch (e: Exception) {
                    errorList.add(e.message ?: "Unknown error")
                }

                // Introduce a delay to control the rate of evaluation
                delay(delayMillis)
            }
        }

        val resultsString = resultsList.joinToString("\n")
        val errorString = errorList.joinToString("\n")

        // Store the results in WorkManager's output data
        val outputData = workDataOf(
            "results" to resultsString,
            "errors" to errorString
        )

        return Result.success(outputData)
    }
}
