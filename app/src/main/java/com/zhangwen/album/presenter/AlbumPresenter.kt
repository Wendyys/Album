package com.zhangwen.album.presenter

import android.content.Context
import android.os.Message
import android.widget.Toast
import com.zhangwen.album.Utils.ImageLoader
import com.zhangwen.album.Utils.WeakHandler
import com.zhangwen.album.view.AlbumView

class AlbumPresenter(var context: Context) : BasePresenter<AlbumView>(), WeakHandler.IHandler {
    private val handler: WeakHandler = WeakHandler(this)
    override fun handleMsg(msg: Message?) {
        when (msg?.what) {
            1 -> {
                var photoList = msg.obj as ArrayList<String>
                if (photoList.size > 0) {
                    mView?.updateAlbumPhoto(photoList)
                } else {
                    Toast.makeText(context, "没有图片", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //获取照片列表
    fun getPhotoList() {
        val imageLoader: ImageLoader = ImageLoader(context.contentResolver, handler)
        var photoList = imageLoader.getImageList()
    }

}