package com.zhangwen.album.Adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewpager.widget.PagerAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.zhangwen.album.Bean.PhotoBean
import com.zhangwen.album.R

class PreviewPagerAdapter(var mContext: Context, var mList: ArrayList<PhotoBean>, source: String) :
    PagerAdapter() {
     val TAG = "PreviewPagerAdapter"
    override fun getCount(): Int {
        return mList.size
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var uri = mList[position].uri
        var view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item, container,false)
        var simpleDraweeView = view.findViewById<SimpleDraweeView>(R.id.preview_image_item)
        simpleDraweeView.setImageURI(Uri.parse("file://$uri"))
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}