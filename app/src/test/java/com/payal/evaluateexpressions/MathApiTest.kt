package com.payal.evaluateexpressions

import com.payal.evaluateexpressions.model.MathApiResponse
import com.payal.evaluateexpressions.repository.ApiResult
import com.payal.evaluateexpressions.repository.MathApiRepository
import com.payal.evaluateexpressions.service.MathApiRequest
import com.payal.evaluateexpressions.service.MathApiService
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MathApiTest {

    private lateinit var mathApiRepository: MathApiRepository

    @Mock
    private lateinit var mathApiService: MathApiService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mathApiRepository = MathApiRepository()
    }

    @Test
    fun `evaluateExpression with successful response`() = runBlocking {
        val expression = "1+2"
        val expectedResult = "3"
        val mathApiResponse = MathApiResponse(result = expectedResult, error = "")
        val response = Response.success(mathApiResponse)

        `when`(mathApiService.evaluateExpression(MathApiRequest(expression))).thenReturn(response)

        val result = mathApiRepository.evaluateExpression(expression)

        assertEquals(ApiResult.Success("3"),result)
    }

    @Test
    fun `evaluateExpression with error response`() = runBlocking {
        val expression = "invalid_expression"
        val errorResponse = Response.error<MathApiResponse>(400, Mockito.mock(ResponseBody::class.java))

        `when`(mathApiService.evaluateExpression(MathApiRequest(expression))).thenReturn(errorResponse)

        val result = mathApiRepository.evaluateExpression(expression)

        assertEquals(ApiResult.Error("$expression => Expression not valid"), result)
    }
}