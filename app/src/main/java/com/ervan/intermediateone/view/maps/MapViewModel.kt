package com.ervan.intermediateone.view.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ervan.intermediateone.data.repository.StoryRepository
import com.ervan.intermediateone.data.responses.ListStoryItem
import kotlinx.coroutines.launch

class MapViewModel(private val repository: StoryRepository) : ViewModel() {

    private val _location = MutableLiveData<List<ListStoryItem>>()
    val location: LiveData<List<ListStoryItem>> = _location

    fun getStoriesLocation() {
        viewModelScope.launch {
            try {
                val response = repository.getStoriesWithLocation()
                _location.value = response.listStory
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val TAG = "MapViewModel"
    }
}