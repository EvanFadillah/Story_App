package com.ervan.intermediateone.view.ViewModelFactories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ervan.intermediateone.data.repository.UploadRepository
import com.ervan.intermediateone.di.Injection
import com.ervan.intermediateone.view.add.UploadViewModel

class UploadViewModelFactory(private val repository: UploadRepository) :
ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                UploadViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UploadViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): UploadViewModelFactory {
            if (INSTANCE == null) {
                synchronized(UploadViewModelFactory::class.java) {
                    INSTANCE = UploadViewModelFactory(Injection.provideUploadRepo(context))
                }
            }
            return INSTANCE as UploadViewModelFactory
        }
    }
}