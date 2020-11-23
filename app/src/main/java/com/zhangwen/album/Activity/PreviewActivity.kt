package com.zhangwen.album.Activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.zhangwen.album.Adapter.PreviewPagerAdapter
import com.zhangwen.album.Adapter.PreviewThumbAdapter
import com.zhangwen.album.Bean.PhotoManager
import com.zhangwen.album.OnThumbItemClickListener
import com.zhangwen.album.Presenter.PreviewPresenter
import com.zhangwen.album.R
import com.zhangwen.album.Utils.Constants
import com.zhangwen.album.View.PreviewView

class PreviewActivity : AppCompatActivity(), PreviewView {
    private lateinit var mBtnBack: LinearLayout
    private lateinit var mTvCurrent: TextView
    private lateinit var mTvTotal: TextView
    private lateinit var mBtnFinish: Button

    private lateinit var mViewPager: ViewPager
    private lateinit var mPreviewPagerAdapter: PreviewPagerAdapter
    private lateinit var mTogglePick: ToggleButton
    private lateinit var mBtnPick: TextView
    private lateinit var mListThumb: RecyclerView
    private lateinit var SOURCE: String
    private var mCurrentPage: Int? = null
    private var TAG = "PreviewActivity"
    private lateinit var mPreviewPresenter: PreviewPresenter
    private lateinit var mThumbListAdapter: PreviewThumbAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        getExtra()
        initView()
        initData()
    }

    private fun getExtra() {
        var bundle = intent.getBundleExtra(Constants.SOURCE)
        if (bundle != null) {
            SOURCE = bundle.get(Constants.SOURCE) as String
            if (SOURCE == Constants.SOURCE_PREVIEW_PHOTO) {
                mCurrentPage = bundle.get(Constants.CURRENT_PAGE) as Int?
            }
        }
    }

    private fun initView() {
        mBtnBack = findViewById(R.id.preview_btn_back)
        mTvCurrent = findViewById(R.id.preview_current_pos)
        mTvTotal = findViewById(R.id.preview_total_number)
        mBtnFinish = findViewById(R.id.preview_btn_finish)
        mViewPager = findViewById(R.id.viewpager)
        mTogglePick = findViewById(R.id.togglebtn_choose)
        mBtnPick = findViewById(R.id.btn_pick)
        mListThumb = findViewById(R.id.preview_list_thumb)
        if (SOURCE == Constants.SOURCE_PREVIEW_BUTTON) {
            mPreviewPagerAdapter =
                PreviewPagerAdapter(this, PhotoManager.photoSelectedList.getAll(), SOURCE)
            mPreviewPresenter =
                PreviewPresenter(
                    this,
                    PhotoManager.photoSelectedList.getAll(),
                    mViewPager,
                    mPreviewPagerAdapter,
                    SOURCE
                )
        } else {
            mPreviewPagerAdapter = PreviewPagerAdapter(this, PhotoManager.getAllPhotoList(), SOURCE)
            mPreviewPresenter = PreviewPresenter(
                this,
                PhotoManager.photoList,
                mViewPager,
                mPreviewPagerAdapter,
                SOURCE
            )
        }
        mViewPager.adapter = mPreviewPagerAdapter
        mCurrentPage?.let { mViewPager.setCurrentItem(it, true) }
        mPreviewPresenter.attach(this)
        mBtnBack.setOnClickListener {
            finish()
        }
        mTogglePick.setOnClickListener {
            mPreviewPresenter.updatePhotoListState(mTogglePick.isChecked, SOURCE)
        }
        //设置预览列表
        val ms = LinearLayoutManager(this)
        ms.orientation = LinearLayoutManager.HORIZONTAL
        mListThumb.layoutManager = ms
        mThumbListAdapter = PreviewThumbAdapter(this, PhotoManager.photoSelectedList.getAll(),
            object : OnThumbItemClickListener {
                override fun OnItemClickListener(adapterPosition: Int) {
                    mPreviewPresenter.setViewPagerCurrentItem(
                        SOURCE,
                        mThumbListAdapter.mList[adapterPosition].position,
                        adapterPosition
                    )
                }
            })
        if (PhotoManager.photoSelectedList.size() > 0) {
            mListThumb.visibility = View.VISIBLE
        } else {
            mListThumb.visibility = View.INVISIBLE
        }
        mListThumb.adapter = mThumbListAdapter

    }

    private fun initData() {
        if (SOURCE == Constants.SOURCE_PREVIEW_BUTTON) {
            //从预览按钮跳转，viewpager数据集设为PhotoManger.photoSelectedList
            mTvTotal.text = PhotoManager.photoSelectedList.size().toString()
            mTvCurrent.text = "1"
            mTogglePick.isChecked = true
        } else {
            //从照片跳转，viewpager数据集设为全部照片
            mTvTotal.text = PhotoManager.photoList.size.toString()
            mTvCurrent.text = mCurrentPage?.plus(1).toString()
            mCurrentPage?.let {
                if (PhotoManager.photoList[it].index != -1)
                    mTogglePick.isChecked = true
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mPreviewPresenter.detach()
    }

    override fun updateCurrentPage(pos: Int) {
        mTvCurrent.text = pos.toString()
    }

    override fun updateToggleButtonState(checked: Boolean) {
        mTogglePick.isChecked = checked
    }

    override fun updateThumbListState(count: Int) {
        if (count > 0) {
            mListThumb.visibility = View.VISIBLE
        } else {
            mListThumb.visibility = View.INVISIBLE
        }
        mThumbListAdapter.mList = PhotoManager.photoSelectedList.getAll()
        mThumbListAdapter.notifyDataSetChanged()
    }

}