package com.example.zhi_hu_news.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

class ViewPagerAdapter constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val list = ArrayList<Banner>()

    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size

    fun inflateData(list: List<Banner>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}