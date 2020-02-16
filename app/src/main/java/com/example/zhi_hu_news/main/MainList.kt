package com.example.zhi_hu_news.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.zhi_hu_news.R
import com.example.zhi_hu_news.detail.WebActivity
import com.example.zhi_hu_news.network.Info
import com.example.zhi_hu_news.network.Story
import com.example.zhi_hu_news.network.TopStory
import java.util.ArrayList

class MainList constructor(
    context: Context,
    fm: FragmentManager,
    private val mainPresenter: MainPresenter
) :
    BaseAdapter(context) {
    private val BANNER_TYPE = 1
    private val DATE_TYPE = 2
    private val MAIN_TYPE = 3
    private val FOOTER_TYPE = 4
    private val mainList = ArrayList<Any>()
    private val dateList = ArrayList<String>()
    private val viewPagerAdapter = ViewPagerAdapter(fm)
    private var isCanScrollVertically: Boolean = true
    fun inflateData(infoBean: Info) {
        mainList.add("今日热闻")
        mainList.addAll(infoBean.stories)
        dateList.add(infoBean.date)
        val topList = ArrayList<TopStory>()
        topList.addAll(infoBean.top_stories)
        val bannerList = ArrayList<Banner>()
        for (i in topList.indices) {
            val bundle = Bundle()
            bundle.putString("image", topList[i].image)
            bundle.putInt("id", topList[i].id)
            bannerList.add(Banner.instance(bundle))
        }
        viewPagerAdapter.inflateData(bannerList)
        notifyDataSetChanged()
    }

    fun refresh() {
        mainList.clear()
        dateList.clear()
        isCanScrollVertically = true
    }

    fun isThereFooter(): Boolean? {
        return itemCount > mainList.size + 1
    }

    fun changeBoolean() {
        isCanScrollVertically = !isCanScrollVertically
        if (!isCanScrollVertically) notifyItemInserted(mainList.size + 1)
    }

    fun update(infoBean: Info) {//用来呼应下拉加载，达到在同一个对象中更新数据目的
        mainList.add(infoBean.date)
        mainList.addAll(infoBean.stories)
        dateList.add(infoBean.date)
        notifyDataSetChanged()
    }

    fun getDate(): String {
        return dateList[dateList.size - 1]
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            BANNER_TYPE
        else if (position < mainList.size + 1 && mainList[position - 1] is String)
            DATE_TYPE
        else if (!isCanScrollVertically && position == itemCount - 1)
            FOOTER_TYPE
        else
            MAIN_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var holder: RecyclerView.ViewHolder
        when (viewType) {
            BANNER_TYPE -> holder = BannerHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.rv_banner,
                    parent,
                    false
                )
            )
            MAIN_TYPE -> holder = MainHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.rv_main,
                    parent,
                    false
                )
            )
            DATE_TYPE -> holder = DateHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.rv_date,
                    parent,
                    false
                )
            )
            FOOTER_TYPE -> holder = FooterHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.rv_footer,
                    parent,
                    false
                )
            )
        }
        return holder
    }

    override fun getItemCount(): Int {
        return if (isCanScrollVertically)
            mainList.size + 1
        else
            mainList.size + 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            BANNER_TYPE -> {
                val bannerHolder = holder as BannerHolder
                bannerHolder.viewPager.adapter = viewPagerAdapter
                bannerHolder.viewPager.offscreenPageLimit = 4
            }
            MAIN_TYPE -> {
                val mainHolder = holder as MainHolder
                val storyBean = mainList[position - 1] as Story
                mainHolder.title.text = storyBean.title
                Glide.with(context)
                    .load(storyBean.images[0])
                    .into(mainHolder.image)
                mainHolder.itemView.setOnClickListener {
                    val intent = Intent(context, WebActivity::class.java)
                    intent.putExtra("id", storyBean.id)
                    context.startActivity(intent)
                }
            }
            DATE_TYPE -> {
                val dateHolder = holder as DateHolder
                dateHolder.date.text = mainList[position - 1] as String
            }
            FOOTER_TYPE -> {
                val footerHolder = holder as FooterHolder
                footerHolder.itemView.setOnClickListener {
                    changeBoolean()
                    notifyItemRemoved(mainList.size + 1)
                    mainPresenter.getBeforeData(getDate())
                }
            }
        }
    }

    internal inner class BannerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewPager: ViewPager = itemView.findViewById(R.id.ViewPager) as ViewPager

    }

    internal inner class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.rv_date_text) as TextView
    }

    internal inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.rv_main_text) as TextView
        var image: ImageView = itemView.findViewById(R.id.rv_main_image) as ImageView
    }

    internal inner class FooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(R.id.rv_footer_text)
    }
}