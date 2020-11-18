package com.zhangwen.album.View

import com.zhangwen.album.Bean.PhotoBean

interface AlbumView : BaseView {
    //更新相册图片
    fun updateAlbumPhoto()
    //更新左下角预览按钮显示的数字
    fun updatePreviewNumber()
}