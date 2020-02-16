package com.example.zhi_hu_news.main
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter(protected var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var layoutInflater: LayoutInflater = LayoutInflater.from(context)
}