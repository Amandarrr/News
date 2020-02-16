package com.example.zhi_hu_news.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.zhi_hu_news.R
import com.example.zhi_hu_news.network.Info

class MainActivity : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: MainList
    private val mainPresenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initId()
        getData()
        refresh()
    }

    fun initId() {
        swipeRefreshLayout = findViewById(R.id.SRL)
        recyclerView = findViewById(R.id.RV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = MainList(this, supportFragmentManager, mainPresenter)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && !recyclerViewAdapter.isThereFooter()!!) {//就很牛逼的判断方法 canScrollVertically -1往下 1往上
                        recyclerViewAdapter.changeBoolean()
                    }
                }
            })
        }
    }

    fun initRV(infoBean: Info) {
        recyclerViewAdapter.inflateData(infoBean)
    }

    fun getData() {
        mainPresenter.getLatestData()
    }

    fun refresh() {//刷新时候直接清空recyclerView和RVAdapter
        swipeRefreshLayout.setOnRefreshListener {
            recyclerViewAdapter.refresh()
            getData()
            Toast.makeText(this, "刷新成功！", Toast.LENGTH_SHORT).show()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    fun loadMore(infoBean: Info) {
        recyclerViewAdapter.update(infoBean)
    }

}
