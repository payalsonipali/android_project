package com.payal.evaluateexpressions.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.payal.evaluateexpressions.dao.HistoryItemDao
import com.payal.evaluateexpressions.entity.HistoryItem

@Database(entities = [HistoryItem::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyItemDao(): HistoryItemDao

    companion object {
        private var instance: HistoryDatabase? = null
        fun getInstance(context: Context): HistoryDatabase {
            if (instance == null) {
                synchronized(HistoryDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_db"
                    ).build()
                }
            }
            return instance!!
        }
    }
}