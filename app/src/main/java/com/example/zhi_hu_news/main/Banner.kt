package com.example.zhi_hu_news.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

import com.bumptech.glide.Glide
import com.example.zhi_hu_news.R
import com.example.zhi_hu_news.detail.WebActivity

class Banner : Fragment() {
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.top_image, container, false)
        imageView = view.findViewById(R.id.top_image_view)
        Glide.with(this)
            .load(arguments?.getString("image"))
            .into(imageView)
        view.setOnClickListener {
            run {
                val intent = Intent(context, WebActivity::class.java)
                intent.putExtra("id", arguments?.getInt("id"))
                context?.startActivity(intent)
            }
        }
        return view
    }

    companion object {
        fun instance(arg: Bundle): Banner {
            val banner = Banner()
            banner.arguments = arg
            return banner
        }
    }
}