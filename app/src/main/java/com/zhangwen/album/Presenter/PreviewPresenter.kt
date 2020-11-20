package com.zhangwen.album.Presenter

import android.content.Context
import androidx.viewpager.widget.ViewPager
import com.zhangwen.album.Bean.PhotoBean
import com.zhangwen.album.View.PreviewView

class PreviewPresenter(
    var mContext: Context,
    var mPhotoList: ArrayList<PhotoBean>,
    var mViewPager: ViewPager,
    var SOURCE: String
) : BasePresenter<PreviewView>() {
    init {
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                mView?.updateCurrentPage(position + 1)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

}