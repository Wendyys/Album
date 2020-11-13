package com.zhangwen.album.view

interface AlbumView : BaseView {
    //更新相册图片
    fun updateAlbumPhoto(photoList: ArrayList<String>)
}