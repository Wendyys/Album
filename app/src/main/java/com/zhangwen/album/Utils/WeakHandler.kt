package com.zhangwen.album.Utils

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Handler use WeakReference to hold callback.
 */
class WeakHandler(looper: Looper?, handler: IHandler?) :
    Handler(looper!!) {
    interface IHandler {
        fun handleMsg(msg: Message?)
    }

    private var mRef: WeakReference<IHandler?> = WeakReference(handler)

    constructor(handler: IHandler?) : this(
        if (Looper.myLooper() == null) Looper.getMainLooper() else Looper.myLooper(),
        handler
    )

    override fun handleMessage(msg: Message) {
        val handler = mRef.get()
        handler?.handleMsg(msg)
    }

}