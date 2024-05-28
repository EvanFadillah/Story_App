package com.ervan.intermediateone.di

import android.content.Context
import com.ervan.intermediateone.data.api.ApiConfig
import com.ervan.intermediateone.data.pref.UserPreference
import com.ervan.intermediateone.data.pref.dataStore
import com.ervan.intermediateone.data.repository.AuthRepository
import com.ervan.intermediateone.data.repository.StoryRepository
import com.ervan.intermediateone.data.repository.UploadRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideAuthentication(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiAuth = ApiConfig.getApiAuth()
        return AuthRepository.getInstance(pref, apiAuth)
    }

    fun provideStoryRepo(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(pref, apiService)
    }

    fun provideUploadRepo(context: Context): UploadRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UploadRepository.getInstance(apiService)
    }
}