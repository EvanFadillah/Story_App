package com.ervan.intermediateone.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ervan.intermediateone.data.pref.UserModel
import com.ervan.intermediateone.data.repository.AuthRepository
import com.ervan.intermediateone.data.responses.LoginResponse
import com.ervan.intermediateone.data.responses.RegisterResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    fun userLogin(email:String, password: String) : LiveData<LoginResponse> {
        val result = MutableLiveData<LoginResponse>()
        viewModelScope.launch {
            val response = repository.userLogin(email,password)
            result.postValue(response)
        }
        return result
    }
}