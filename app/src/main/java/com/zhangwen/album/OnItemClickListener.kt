package com.zhangwen.album

import android.view.View

interface OnItemClickListener {
    fun onPhotoClick(view: View, pos: Int)
    fun onToggleClick(view: View, pos: Int)
}