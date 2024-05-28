package com.ervan.intermediateone.view.add

import androidx.lifecycle.ViewModel
import com.ervan.intermediateone.data.repository.UploadRepository
import java.io.File

class UploadViewModel(private val repository: UploadRepository) : ViewModel() {
    fun uploadImage(file: File, description: String) = repository.uploadImage(file, description)
}