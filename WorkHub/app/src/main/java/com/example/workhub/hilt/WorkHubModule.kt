package com.example.workhub.hilt

import com.example.workhub.data.retrofit.BASE_URL
import com.example.workhub.data.retrofit.WorkHubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkHubModule {

    @Singleton
    @Provides
    fun provideImpressionsApi(): WorkHubApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(WorkHubApi::class.java)
    }

}
