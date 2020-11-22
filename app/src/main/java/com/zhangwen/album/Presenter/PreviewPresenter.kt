package com.zhangwen.album.Presenter

import android.content.Context
import android.util.Log
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
                Log.d("PreviewPresenter", "onPageSelected")
                //更新左上角页码
                mView?.updateCurrentPage(position + 1)
                updatePickState(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun updatePickState(position: Int) {
        //更新右下角选中状态
        if (mPhotoList[position].index != -1) {
            mView?.updateToggleButtonState(true)
        } else {
            mView?.updateToggleButtonState(false)
        }
    }

}