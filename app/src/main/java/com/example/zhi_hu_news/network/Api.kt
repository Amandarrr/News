package com.example.zhi_hu_news.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("latest")
    fun getLatest(): Call<Info>

    @GET("before/{date}")
    fun getBefore(@Path("date") date: String): Call<Info>
}