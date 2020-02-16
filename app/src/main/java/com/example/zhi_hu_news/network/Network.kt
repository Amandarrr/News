package com.example.zhi_hu_news.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    private const val BASE_URL = "https://news-at.zhihu.com/api/3/news/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    fun getLatest(): Call<Info> = retrofit.create(Api::class.java).getLatest()
    fun getBefore(date: String): Call<Info> = retrofit.create(Api::class.java).getBefore(date)
}