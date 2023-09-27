package com.payal.evaluateexpressions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.payal.evaluateexpressions.dao.HistoryItemDao
import com.payal.evaluateexpressions.entity.HistoryItem
import com.payal.evaluateexpressions.repository.HistoryItemRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class HistoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var historyItemRepository: HistoryItemRepository

    @Mock
    private lateinit var historyItemDao: HistoryItemDao

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        historyItemRepository = HistoryItemRepository(historyItemDao)
    }

    @Test
    fun testInsertHistoryItem() = runBlocking {
        val historyItem = HistoryItem(id = 1, expression = "1+2", submissionDate = 166666666, result = "3")

        // Mock the DAO's insert function
        `when`(historyItemDao.insert(historyItem)).thenReturn(Unit)

        // Insert the item into the repository
        historyItemRepository.insertHistoryItem(historyItem)

        // Verify that the DAO's insert function was called with the correct item
        Mockito.verify(historyItemDao).insert(historyItem)
    }

    @Test
    fun testGetAllHistoryItems() {
        val historyItemList = listOf(
            HistoryItem(id = 1, expression = "1+2", submissionDate = 166666666,result = "3"),
            HistoryItem(id = 2, expression = "2+3",submissionDate = 166666666, result = "5")
        )
        val liveData = MutableLiveData<List<HistoryItem>>()
        liveData.postValue(historyItemList)

        `when`(historyItemDao.getAllHistoryItems()).thenReturn(liveData)
        val result: LiveData<List<HistoryItem>> = historyItemRepository.getAllHistoryItems()

        assert(result.value == historyItemList)
    }
}