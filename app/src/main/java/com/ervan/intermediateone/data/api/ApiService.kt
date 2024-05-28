package com.ervan.intermediateone.data.api


import com.ervan.intermediateone.data.responses.FileUploadResponse
import com.ervan.intermediateone.data.responses.ListStoryItem
import com.ervan.intermediateone.data.responses.LoginResponse
import com.ervan.intermediateone.data.responses.MapsResponse
import com.ervan.intermediateone.data.responses.RegisterResponse
import com.ervan.intermediateone.data.responses.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
  @FormUrlEncoded
  @POST("register")
  suspend fun register(
     @Field("name") name: String,
     @Field("email") email: String,
     @Field("password") password: String
 ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): StoryResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location : Int = 1,
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): FileUploadResponse
}

