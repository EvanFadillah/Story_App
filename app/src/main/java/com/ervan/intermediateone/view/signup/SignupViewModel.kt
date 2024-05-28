package com.ervan.intermediateone.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ervan.intermediateone.data.responses.RegisterResponse
import com.ervan.intermediateone.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: AuthRepository) : ViewModel() {

    fun userRegister(name:String, email:String, password: String): LiveData<RegisterResponse> {
        val result = MutableLiveData<RegisterResponse>()
        viewModelScope.launch {
            try {
                val response = repository.userRegister(name, email, password)
                result.postValue(response)
            } catch (e: Exception) {
                result.postValue(RegisterResponse(false,"Telah terjadi error"))
            }
        }
        return result
    }
}
