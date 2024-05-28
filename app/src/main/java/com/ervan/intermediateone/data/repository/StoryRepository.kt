package com.ervan.intermediateone.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ervan.intermediateone.data.StoryPagingSource
import com.ervan.intermediateone.data.api.ApiService
import com.ervan.intermediateone.data.pref.UserModel
import com.ervan.intermediateone.data.pref.UserPreference
import com.ervan.intermediateone.data.responses.ListStoryItem
import com.ervan.intermediateone.data.responses.MapsResponse
import com.ervan.intermediateone.data.responses.RegisterResponse
import com.ervan.intermediateone.data.responses.StoryResponse

class StoryRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
){

    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    suspend fun getStoriesWithLocation(): StoryResponse {
        return apiService.getStoriesWithLocation()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(userPreference, apiService)
            }.also { instance = it }
    }
}