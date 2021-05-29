package com.example.imagesearchapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val  BASE_URL = "https://api.unsplash.com/"
private const val ACCESS_KEY = "D6LFzCnLFJQnrEqXFUFkYZ_GuIhfkZgs88z-A4x9XhY";

private val retrofit = Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(BASE_URL)
                            .build()

interface UnsplashApiService{

    @Headers("Accept-Version: v1", "Authorization: Client-ID $ACCESS_KEY")
    @GET("search/photos")
    suspend fun getData(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int) : UnsplashResponse
}

object UnsplashApi{
    val retrofitService = retrofit.create(UnsplashApiService::class.java)
}

