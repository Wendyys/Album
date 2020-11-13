package com.zhangwen.album.Utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.facebook.common.util.UriUtil

object ImageLoader {
    private val TAG = "ImageLoader"

    //读取相册图片Uri列表
    fun getImageList(contentResolver: ContentResolver): ArrayList<String> {
        val resolver: ContentResolver = contentResolver
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val sort: String = MediaStore.Images.Media._ID + " DESC";
        val cursor: Cursor? = resolver.query(uri, null, null, null, sort)
        val imageUri: ArrayList<String> = ArrayList()
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        val imgId =
                            cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                        val path: Uri = Uri.withAppendedPath(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "" + imgId
                        );
                        UriUtil.getRealPathFromUri(resolver, path)?.let {
                            Log.d(TAG, it)
                            imageUri.add(it)
                        }
                    } while (cursor.moveToNext())
                }
                cursor.close()
            } catch (e: Exception) {
                if (!cursor.isClosed)
                    cursor.close()
            }
        }
        return imageUri
    }

}