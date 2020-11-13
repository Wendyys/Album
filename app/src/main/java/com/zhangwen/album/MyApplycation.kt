package com.zhangwen.album

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class MyApplycation : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}