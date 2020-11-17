package com.zhangwen.album.View

interface AlbumView : BaseView {
    //更新相册图片
    fun updateAlbumPhoto(photoList: ArrayList<String>)
}