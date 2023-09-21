package com.example.workhub.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [LocalChat::class, LocalMessage::class], version = 1, exportSchema = false)
abstract class WorkHubDatabase : RoomDatabase() {
    abstract fun workHubDao(): WorkHubDao

    companion object {
        private var INSTANCE: WorkHubDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WorkHubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkHubDatabase::class.java,
                    "work_hub_database"
                ).addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                return@synchronized instance
            }
        }
    }

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch { populateDatabase(database.workHubDao()) }
            }
        }

        suspend fun populateDatabase(wordDao: WorkHubDao) {

        }
    }
}
