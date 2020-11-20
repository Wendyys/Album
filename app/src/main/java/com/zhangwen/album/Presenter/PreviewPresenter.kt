package com.zhangwen.album.Presenter

import android.content.Context
import com.zhangwen.album.Bean.PhotoBean
import com.zhangwen.album.View.PreviewView

class PreviewPresenter(var mContext: Context,var mPhotoList:ArrayList<PhotoBean>) : BasePresenter<PreviewView>() {

}