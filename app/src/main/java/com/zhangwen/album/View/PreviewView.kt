package com.zhangwen.album.View

interface PreviewView : BaseView {
    fun updateCurrentPage(pos: Int)
    fun updateToggleButtonState(checked: Boolean)
}