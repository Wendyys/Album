package com.zhangwen.album.Bean

/**
 * position:在列表中的位置
 * index:在选中的list中的位置
 */
class PhotoBean(var position: Int, var uri: String, var index: Int)

object PhotoManager {
    var photoList = ArrayList<PhotoBean>()
    var photoSelectedList = PhotoSelectedList
    fun release() {
        photoList.clear()
        photoSelectedList.clear()
    }

    fun getAllPhotoList(): ArrayList<PhotoBean> {
        var all = ArrayList<PhotoBean>()
        all.addAll(photoList)
        return all
    }
}