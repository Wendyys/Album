package com.zhangwen.album

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.common.util.UriUtil
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.zhangwen.album.Utils.Constants
import com.zhangwen.album.Utils.SpaceItemDecoration


class MainActivity : AppCompatActivity() {
    private val TAG: String = "Album"
    private lateinit var mImageList: RecyclerView
    private lateinit var mImageAdapter: ImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPermission(this, true, true)
    }

    //读取相册图片Uri列表
    private fun getImageList(): ArrayList<String> {
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

    //设置ListView布局
    private fun setImageListLayout() {
        val gridLayoutManager: GridLayoutManager =
            GridLayoutManager(this, Constants.columns, GridLayoutManager.VERTICAL, false)
        val spaceItemDecoration: SpaceItemDecoration = SpaceItemDecoration(5)
        mImageList = findViewById(R.id.image_list)
        mImageAdapter = ImageAdapter(getImageList(), this)
        mImageList.adapter = mImageAdapter
        mImageList.layoutManager = gridLayoutManager
        mImageList.addItemDecoration(spaceItemDecoration)
    }
    //申请权限
    private fun getPermission(context: Context, isAsk: Boolean, isHandOpen: Boolean) {
        if (!isAsk) return
        if (XXPermissions.isHasPermission(
                context,  //所需危险权限可以在此处添加：
                Permission.READ_PHONE_STATE,
                Permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Log.e(TAG, "已经获得所需所有权限")
        } else {
            XXPermissions.with(context as Activity).permission( //同时在此处添加：
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE
            ).request(object : OnPermission {
                override fun noPermission(denied: List<String?>?, quick: Boolean) {
                    Toast.makeText(this@MainActivity, "获取权限失败", Toast.LENGTH_SHORT).show()
                }

                override fun hasPermission(granted: List<String?>?, isAll: Boolean) {
                    if (isAll) {
                        setImageListLayout()
                    } else {
                        Toast.makeText(this@MainActivity, "部分权限获取失败", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

}