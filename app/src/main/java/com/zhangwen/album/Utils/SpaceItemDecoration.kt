package com.zhangwen.album.Utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration : RecyclerView.ItemDecoration {
    private var space = 0

    constructor(space: Int) {
        this.space = space
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space;
        outRect.bottom = space;
        if (parent.getChildLayoutPosition(view) % Constants.COLUMNS == 0)
            outRect.left = 0
    }

}