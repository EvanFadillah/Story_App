package com.ervan.intermediateone.view.home_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ervan.intermediateone.data.api.ApiConfig
import com.ervan.intermediateone.data.repository.StoryRepository
import com.ervan.intermediateone.data.responses.ListStoryItem
import com.ervan.intermediateone.data.responses.StoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class HomePageViewModel(private val repository: StoryRepository) : ViewModel() {

    val stories: LiveData<PagingData<ListStoryItem>> =
        repository.getStories().cachedIn(viewModelScope)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}