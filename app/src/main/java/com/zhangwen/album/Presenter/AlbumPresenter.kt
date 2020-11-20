package com.zhangwen.album.Presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import com.zhangwen.album.Activity.PreviewActivity
import com.zhangwen.album.Bean.PhotoBean
import com.zhangwen.album.Bean.PhotoManager
import com.zhangwen.album.OnItemClickListener
import com.zhangwen.album.Utils.Constants
import com.zhangwen.album.Utils.ImageLoader
import com.zhangwen.album.Utils.WeakHandler
import com.zhangwen.album.View.AlbumView

class AlbumPresenter(var context: Context, var imageList: ArrayList<PhotoBean>) :
    BasePresenter<AlbumView>(), WeakHandler.IHandler,
    OnItemClickListener {
    private val handler: WeakHandler = WeakHandler(this)
    override fun handleMsg(msg: Message?) {
        when (msg?.what) {
            1 -> {
                var photoList = msg.obj as ArrayList<String>
                if (photoList.size > 0) {
                    for (i in 0 until photoList.size) {
                        var photoBean = PhotoBean(imageList.size + i, photoList[i], -1)
                        imageList.add(photoBean)
                    }
                    mView?.updateAlbumPhoto()
                } else {
                    Toast.makeText(context, "没有图片", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //获取照片列表
    fun getPhotoList() {
        val imageLoader: ImageLoader = ImageLoader(context.contentResolver, handler)
        imageLoader.getImageList()
    }

    //处理点击事件
    fun jump2preview(activity: Activity, source: String?, current: Int?) {
        var intent = Intent(activity, PreviewActivity::class.java)
        if (source != null) {
            var bundle = Bundle()
            bundle.putString(Constants.SOURCE, source)
            current?.let { bundle.putInt(Constants.CURRENT_PAGE, it) }
            intent.putExtra(Constants.SOURCE, bundle)

        }
        activity.startActivityForResult(intent, 666)
    }


    override fun onPhotoClick(view: View, pos: Int, uri: String) {
        jump2preview(context as Activity, Constants.SOURCE_PREVIEW_PHOTO, pos)
    }

    override fun onToggleClick(view: View, pos: Int, uri: String, checked: Boolean) {
        var index = PhotoManager.photoSelectedList.size() + 1
        var selected: PhotoBean = PhotoBean(pos, uri, index)
        if (checked) {
            PhotoManager.photoSelectedList.add(selected)
            val toggle = view as ToggleButton
            toggle.text = index.toString()
            imageList[pos].index = index
        } else {
            PhotoManager.photoSelectedList.delete(selected)
            imageList[pos].index = -1
            //更新列表值
            var size = PhotoManager.photoSelectedList.size()
            for (i in 0 until size) {
                imageList[PhotoManager.photoSelectedList.get(i).position].index =
                    PhotoManager.photoSelectedList.get(i).index
            }
        }
        mView?.updatePreviewNumber()

    }

}