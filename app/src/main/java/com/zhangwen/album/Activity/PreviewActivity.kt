package com.zhangwen.album.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.zhangwen.album.Bean.PhotoManager
import com.zhangwen.album.R
import com.zhangwen.album.Utils.Constants

class PreviewActivity : AppCompatActivity() {
    private lateinit var mBtnBack: Button
    private lateinit var mTvCurrent: TextView
    private lateinit var mTvTotal: TextView
    private lateinit var mBtnFinish: Button

    private lateinit var mViewPager: ViewPager
    private lateinit var mTogglePick: ToggleButton
    private lateinit var mBtnPick: TextView

    private lateinit var SOURCE: String
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
    }
    private fun initData(){
        if(SOURCE == Constants.PREVIEW_BUTTON){
            //从预览按钮跳转，viewpager数据集设为PhotoManger.photoSelectedList
            mTvTotal.text = PhotoManager.photoSelectedList.size().toString()
        }else{
            //从照片跳转，viewpager数据集设为全部照片

        }
    }
}