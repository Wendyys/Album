package com.zhangwen.album.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.zhangwen.album.Adapter.AlbumAdapter
import com.zhangwen.album.Bean.PhotoManager
import com.zhangwen.album.Presenter.AlbumPresenter
import com.zhangwen.album.R
import com.zhangwen.album.Utils.Constants
import com.zhangwen.album.Utils.SpaceItemDecoration
import com.zhangwen.album.View.AlbumView


class MainActivity : AlbumView, AppCompatActivity() {
    private val TAG: String = "Album"
    private lateinit var mImageList: RecyclerView
    private lateinit var mAlbumAdapter: AlbumAdapter
    private lateinit var mAlbumPresenter: AlbumPresenter
    private lateinit var mPreviewCount: TextView
    private lateinit var mPreview: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAlbumPresenter = AlbumPresenter(this, PhotoManager.photoList)
        mAlbumPresenter.attach(this)
        init()
        getPermission(this, true, true)

    }


    private fun init() {
        val gridLayoutManager: GridLayoutManager =
            GridLayoutManager(this, Constants.COLUMNS, GridLayoutManager.VERTICAL, false)
        val spaceItemDecoration: SpaceItemDecoration = SpaceItemDecoration(5)
        mImageList = findViewById(R.id.image_list)
        mAlbumAdapter = AlbumAdapter(PhotoManager.photoList, this, mAlbumPresenter)
        mImageList.adapter = mAlbumAdapter
        mImageList.layoutManager = gridLayoutManager
        mImageList.addItemDecoration(spaceItemDecoration)
        mPreviewCount = findViewById(R.id.tv_choose_count)
        mPreview = findViewById(R.id.tv_preview)
        mPreview.alpha = Constants.DISABLE_ALPHA
        mPreview.setOnClickListener(View.OnClickListener {
            jump2preview(Constants.PREVIEW_BUTTON)
        })
    }

    private fun jump2preview(source: String?) {
        var intent = Intent(this, PreviewActivity::class.java)
        if (source != null) {
            var bundle = Bundle()
            bundle.putString(Constants.SOURCE, source)
            intent.putExtra(Constants.SOURCE, bundle)
        }
        startActivityForResult(intent, 666)
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
                        mAlbumPresenter.getPhotoList()
                    } else {
                        Toast.makeText(this@MainActivity, "部分权限获取失败", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun updateAlbumPhoto() {

        mAlbumAdapter.notifyItemRangeChanged(0, 1, Constants.PAYLOAD_TOGGLE)

    }

    override fun updatePreviewNumber() {
        if (PhotoManager.photoSelectedList.size() > 0) {
            mPreviewCount.visibility = View.VISIBLE
            mPreviewCount.text = "(${PhotoManager.photoSelectedList.size()})"
            mPreview.alpha = Constants.ENABLE_ALPHA
            //更新图片的数字序号
            for (i in 0 until PhotoManager.photoSelectedList.size()) {
                mAlbumAdapter.notifyItemChanged(
                    PhotoManager.photoSelectedList.selectedList[i].position,
                    Constants.PAYLOAD_TOGGLE
                )
            }
        } else {
            mPreviewCount.visibility = View.INVISIBLE
            mPreview.alpha = Constants.DISABLE_ALPHA
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity.onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity.onDestory")
        mAlbumPresenter.detach()
        PhotoManager.release()
    }
}