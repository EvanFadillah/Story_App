package com.ervan.intermediateone.data.repository

import android.content.Intent
import android.util.Log
import com.ervan.intermediateone.data.api.ApiService
import com.ervan.intermediateone.data.responses.RegisterResponse
import com.ervan.intermediateone.data.pref.UserModel
import com.ervan.intermediateone.data.pref.UserPreference
import com.ervan.intermediateone.data.responses.LoginResponse
import com.ervan.intermediateone.view.signup.SignupActivity
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class AuthRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun userRegister(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun userLogin(email: String, password: String): LoginResponse {
        var response = LoginResponse(error = true, message = "")
        try {
             response = apiService.login(email ,password)
            if (response.error != true) {
                userPreference.saveSession(UserModel(email = email, token = response.loginResult?.token.toString(),
                isLogin = true))
            } else {
            // Handle error
                Log.e("Login Error", response.message)
            }
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Log.e("Login Error", "Unauthorized")
            } else {
                Log.e("Login Error", "HTTP error code: ${e.code()}")
            }
        }
        return response
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }


    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(userPreference, apiService)
            }.also { instance = it }
    }
}