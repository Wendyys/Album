package com.zhangwen.album.Presenter

import com.zhangwen.album.View.AlbumView
import com.zhangwen.album.View.BaseView

abstract class BasePresenter<T : BaseView> {
    open var mView: AlbumView? = null

    fun attach(view: AlbumView) {
        mView = view
    }

    fun detach() {
        mView = null
    }
}