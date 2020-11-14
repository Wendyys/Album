package com.zhangwen.album.Utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Message
import android.provider.MediaStore
import com.facebook.common.util.UriUtil

class ImageLoader(var contentResolver: ContentResolver, var mHandler: WeakHandler) {
    private val TAG = "ImageLoader"
    fun getImageList() {
        PhotoLoadTask().execute()
    }

    //异步读取图片uri
    inner class PhotoLoadTask : AsyncTask<Void, Int, Boolean>() {
        override fun doInBackground(vararg p0: Void?): Boolean {
            try {
                val resolver: ContentResolver = contentResolver
                val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val sort: String = MediaStore.Images.Media._ID + " DESC";
                val cursor: Cursor? = resolver.query(uri, null, null, null, sort)
                var imageUri: ArrayList<String> = ArrayList()
                if (cursor != null) {
                    try {
                        if (cursor.moveToFirst()) {
                            var counter = 0
                            do {
                                val imgId =
                                    cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                                val path: Uri = Uri.withAppendedPath(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    "" + imgId
                                );
                                UriUtil.getRealPathFromUri(resolver, path)?.let {
                                    imageUri.add(it)
                                }
                                counter++
                                //20条数据一更新，防止界面出现大片空白..
                                if (counter == Constants.PHOTO_BATCH) {
                                    var msg = Message()
                                    msg.what = 1
                                    msg.obj = imageUri
                                    mHandler.sendMessage(msg)
                                    //列表清空
                                    imageUri = ArrayList()
                                    counter = 0
                                }
                            } while (cursor.moveToNext())
                        }
                        cursor.close()
                    } catch (e: java.lang.Exception) {
                        if (!cursor.isClosed)
                            cursor.close()
                    }
                }
                var msg = Message()
                msg.what = 1
                msg.obj = imageUri
                mHandler.sendMessage(msg)
                return true
            } catch (e: java.lang.Exception) {
                return false
            }
            return false
        }
    }

}