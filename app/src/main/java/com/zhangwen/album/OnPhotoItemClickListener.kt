package com.zhangwen.album

import android.view.View

interface OnPhotoItemClickListener {
    fun onPhotoClick(view: View, pos: Int, uri: String)
    fun onToggleClick(view: View, pos: Int, uri: String, checked: Boolean)
}