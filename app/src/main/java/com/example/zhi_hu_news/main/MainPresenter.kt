package com.example.zhi_hu_news.main

import android.util.Log
import android.widget.Toast
import com.example.zhi_hu_news.network.Info
import com.example.zhi_hu_news.network.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter constructor(mainActivity:MainActivity) {
    private val mainActivity=mainActivity


    fun getLatestData() {
        Network.getLatest().enqueue(object : Callback<Info> {
            override fun onResponse(call: Call<Info>, response: Response<Info>) {
                response.body()?.let { mainActivity.initRV(it) }
            }

            override fun onFailure(call: Call<Info>, t: Throwable) {
                Log.e("Retrofit", "错误", t)
                Toast.makeText(mainActivity, "好像出现错误啦", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getBeforeData(date: String) {
        Network.getBefore(date).enqueue(object : Callback<Info> {
            override fun onResponse(call: Call<Info>, response: Response<Info>) {
                response.body()?.let { mainActivity.loadMore(it) }
            }

            override fun onFailure(call: Call<Info>, t: Throwable) {
                Log.e("Retrofit", "错误", t)
                Toast.makeText(mainActivity, "好像出现错误啦", Toast.LENGTH_SHORT).show()
            }
        })
    }
}