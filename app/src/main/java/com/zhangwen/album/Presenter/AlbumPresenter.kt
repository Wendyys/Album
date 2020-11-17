package com.zhangwen.album.Presenter

import android.content.Context
import android.os.Message
import android.view.View
import android.widget.Toast
import com.zhangwen.album.OnItemClickListener
import com.zhangwen.album.Utils.ImageLoader
import com.zhangwen.album.Utils.WeakHandler
import com.zhangwen.album.View.AlbumView

class AlbumPresenter(var context: Context) : BasePresenter<AlbumView>(), WeakHandler.IHandler ,OnItemClickListener{
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

    override fun onPhotoClick(view: View, pos: Int) {
        Toast.makeText(context,"点击了"+pos+"的照片",Toast.LENGTH_SHORT).show()
    }

    override fun onToggleClick(view: View, pos: Int) {
        Toast.makeText(context,"点击了"+pos+"的按钮",Toast.LENGTH_SHORT).show()
    }

}