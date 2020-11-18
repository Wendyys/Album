package com.zhangwen.album.View

interface AlbumView : BaseView {
    //更新相册图片
    fun updateAlbumPhoto(photoList: ArrayList<String>)
    //更新左下角预览按钮显示的数字
    fun updatePreviewNumber()
}