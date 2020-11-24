package com.zhangwen.album.Presenter

import android.content.Context
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.zhangwen.album.Adapter.PreviewPagerAdapter
import com.zhangwen.album.Bean.PhotoBean
import com.zhangwen.album.Bean.PhotoManager
import com.zhangwen.album.Utils.Constants
import com.zhangwen.album.View.PreviewView

class PreviewPresenter(
    var mContext: Context,
    var mPhotoList: ArrayList<PhotoBean>,
    var mViewPager: ViewPager,
    var mViewPagerAdapter: PreviewPagerAdapter,
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
        mView?.updateThumbListState(PhotoManager.photoSelectedList.size())
    }

    fun updatePhotoListState(checked: Boolean, source: String) {
        if (checked) {

            PhotoManager.photoSelectedList.selectedList.add(mPhotoList[mViewPager.currentItem])
            mPhotoList[mViewPager.currentItem].index = PhotoManager.photoSelectedList.size()

        } else {
            PhotoManager.photoSelectedList.delete(mPhotoList[mViewPager.currentItem])
            mPhotoList[mViewPager.currentItem].index = -1
        }
        mView?.updateThumbListState(PhotoManager.photoSelectedList.size())
    }

    //点击图片跳转到viewpager指定页
    fun setViewPagerCurrentItem(SOURCE: String, absolutePos: Int, relativePos: Int) {
        Log.d("PreviewPresenter", "absolutePos:$absolutePos relativePos:$relativePos")
        if (SOURCE == Constants.SOURCE_PREVIEW_BUTTON) {
            for (i in 0 until mPhotoList.size) {
                if (mPhotoList[i].position == absolutePos) {
                    mViewPager.setCurrentItem(i, false)
                    break;
                }
            }
        } else {
            mViewPager.setCurrentItem(absolutePos, false)
        }
    }


}