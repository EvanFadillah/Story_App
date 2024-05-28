package com.ervan.intermediateone.view.ViewModelFactories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ervan.intermediateone.data.repository.StoryRepository
import com.ervan.intermediateone.di.Injection
import com.ervan.intermediateone.view.home_page.HomePageViewModel
import com.ervan.intermediateone.view.maps.MapViewModel

class StoryViewModelFactory(private val repository: StoryRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomePageViewModel::class.java) -> {
                HomePageViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MapViewModel::class.java) -> {
                MapViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): StoryViewModelFactory {
            if (INSTANCE == null) {
                synchronized(StoryViewModelFactory::class.java) {
                    INSTANCE = StoryViewModelFactory(Injection.provideStoryRepo(context))
                }
            }
            return INSTANCE as StoryViewModelFactory
        }
    }
}