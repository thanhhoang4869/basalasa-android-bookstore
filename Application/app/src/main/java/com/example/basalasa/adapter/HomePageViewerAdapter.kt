package com.example.basalasa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.basalasa.R

class HomePageViewerAdapter(context: Context): PagerAdapter() {
    var images = intArrayOf(R.drawable.slider_1,R.drawable.slider_2)
    var layoutInflater: LayoutInflater? = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == (`object` as ConstraintLayout)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = layoutInflater?.inflate(R.layout.home_page_viewer_item, container, false)
        val homeImageView = itemView?.findViewById<ImageView>(R.id.homeSliderItem)

        homeImageView?.setImageResource(images[position])
        container.addView(itemView)
        return itemView!!
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

}