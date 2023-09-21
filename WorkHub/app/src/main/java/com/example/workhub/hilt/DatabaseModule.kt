package com.example.workhub.hilt

import android.content.Context
import com.example.workhub.data.localdb.WorkHubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(coroutineScope: CoroutineScope, @ApplicationContext context: Context) =
        WorkHubDatabase.getDatabase(context, coroutineScope)

    @Provides
    @Singleton
    fun providesWordDao(database: WorkHubDatabase) =
        database.workHubDao()
}