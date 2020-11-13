package com.zhangwen.album.presenter

import android.content.Context
import android.os.Message
import android.widget.Toast
import com.bytedance.common.utility.collection.WeakHandler
import com.zhangwen.album.Utils.ImageLoader
import com.zhangwen.album.view.AlbumView

class AlbumPresenter(var context: Context) : BasePresenter<AlbumView>(),WeakHandler.IHandler {
    private val handler: WeakHandler = WeakHandler(this)
    private lateinit var  photoList:ArrayList<String>
    override fun handleMsg(msg: Message?) {

    }

    //获取照片列表
    fun getPhotoList(){
        photoList = ImageLoader.getImageList(context.contentResolver)
        if(photoList.size>0){
            mView?.updateAlbumPhoto(photoList)
        }else{
            Toast.makeText(context,"没有图片",Toast.LENGTH_SHORT).show()
        }
    }

}