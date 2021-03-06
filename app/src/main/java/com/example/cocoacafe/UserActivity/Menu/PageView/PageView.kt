package com.example.cocoacafe.UserActivity.Menu.PageView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.cocoacafe.R

class PageView : PagerAdapter {

    lateinit var con : Context
    lateinit var path : IntArray
    lateinit var inflater: LayoutInflater

    constructor(con: Context, path: IntArray) : super() {
        this.con = con
        this.path = path
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view == `object` as RelativeLayout

    }

    override fun getCount(): Int {

        return path.size

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val img : ImageView
        inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var rv : View = inflater.inflate(R.layout.swipe_fragment,container,false)
        img = rv.findViewById(R.id.swipeImage) as ImageView
        img.setImageResource(path[position])
        container!!.addView(rv)
        return rv

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as RelativeLayout)
    }

}