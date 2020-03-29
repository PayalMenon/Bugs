package com.joinhonor.bugtracker.dagger

import com.joinhonor.bugtracker.ApiRepository
import com.joinhonor.bugtracker.BugService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private val BASE_URL = "http://bugs.honor-projects.com:5000/api/"
    }

    @Singleton
    @Provides
    fun providesRetrofitService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): BugService {
        return retrofit.create<BugService>(BugService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiRepository(bugService: BugService): ApiRepository {
        return ApiRepository(bugService)
    }
}