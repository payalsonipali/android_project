package com.payal.evaluateexpressions.repository

import com.payal.evaluateexpressions.service.MathApiRequest
import com.payal.evaluateexpressions.util.RetrofitClient

class MathApiRepository {
    private val mathApiService = RetrofitClient.mathApiService

     suspend fun evaluateExpression(expression: String): ApiResult<String> {
        return try {
            val response = mathApiService.evaluateExpression(MathApiRequest(expression))
            if (response.isSuccessful) {
                val mathApiResponse = response.body()
                mathApiResponse?.result?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Error("Empty response")
            } else {
                if (response.code() == 500) {
                    ApiResult.Error("Internal server error")
                } else {
                    ApiResult.Error("$expression => Expression not valid")
                }
            }
        } catch (e: Exception) {
            ApiResult.Error("${e.message}")
        }
    }
}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}
