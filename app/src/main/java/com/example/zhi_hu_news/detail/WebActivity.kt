package com.example.zhi_hu_news.detail

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.zhi_hu_news.R

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        /*取出传入intent的参数*/
        val id = this.intent.extras?.getInt("id")
        val webView = findViewById<WebView>(R.id.WebView)
        webView.webViewClient = WebViewClient()
        /*加载网页*/
        webView.loadUrl("https://daily.zhihu.com/story/$id")
    }
}