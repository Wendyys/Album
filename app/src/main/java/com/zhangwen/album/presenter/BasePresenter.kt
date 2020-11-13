package com.zhangwen.album.presenter

import com.zhangwen.album.view.AlbumView
import com.zhangwen.album.view.BaseView

abstract class BasePresenter<T : BaseView> {
    open var mView: AlbumView? = null

    fun attach(view: AlbumView) {
        mView = view
    }

    fun detach() {
        mView = null
    }
}