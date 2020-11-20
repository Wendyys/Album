package com.zhangwen.album.Presenter

import com.zhangwen.album.View.BaseView

abstract class BasePresenter<T : BaseView> {
    open var mView: T? = null

    fun attach(view: T) {
        mView = view
    }

    fun detach() {
        mView = null
    }
}