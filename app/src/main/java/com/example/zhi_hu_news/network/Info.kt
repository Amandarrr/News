package com.example.zhi_hu_news.network

data class Info(
    val date: String,
    val stories: List<Story>,
    val top_stories: List<TopStory>
)