package com.payal.evaluateexpressions.service

import com.payal.evaluateexpressions.model.MathApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MathApiService {

    @POST("v4/")
    suspend fun evaluateExpression(@Body request: MathApiRequest): Response<MathApiResponse>
}

data class MathApiRequest(val expr: String)
