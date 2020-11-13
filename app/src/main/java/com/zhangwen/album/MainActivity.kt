package com.zhangwen.album

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.zhangwen.album.Utils.Constants
import com.zhangwen.album.Utils.ImageLoader
import com.zhangwen.album.Utils.SpaceItemDecoration
import com.zhangwen.album.presenter.AlbumPresenter
import com.zhangwen.album.view.AlbumView


class MainActivity : AlbumView, AppCompatActivity() {
    private val TAG: String = "Album"
    private lateinit var mImageList: RecyclerView
    private lateinit var mImageAdapter: ImageAdapter
    private lateinit var albumPresenter: AlbumPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        albumPresenter = AlbumPresenter(this)
        albumPresenter.attach(this)
        getPermission(this, true, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        albumPresenter.detach()
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
                        albumPresenter.getPhotoList()
                    } else {
                        Toast.makeText(this@MainActivity, "部分权限获取失败", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun updateAlbumPhoto(photoList: ArrayList<String>) {
        val gridLayoutManager: GridLayoutManager =
            GridLayoutManager(this, Constants.columns, GridLayoutManager.VERTICAL, false)
        val spaceItemDecoration: SpaceItemDecoration = SpaceItemDecoration(5)
        mImageList = findViewById(R.id.image_list)
        mImageAdapter = ImageAdapter(photoList, this)
        mImageList.adapter = mImageAdapter
        mImageList.layoutManager = gridLayoutManager
        mImageList.addItemDecoration(spaceItemDecoration)
    }

}