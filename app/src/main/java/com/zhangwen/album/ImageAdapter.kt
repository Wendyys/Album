package com.zhangwen.album

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView

class ImageAdapter(private val mImageList: ArrayList<String>, private val mContext: Context) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private val TAG: String = "Album"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        var image = mImageList[position]
        var tag = holder.mImageView.tag
        if (tag != image) {
            holder.mImageView.tag = image
            //一定要加上前面的schema 要不然图片不显示
            holder.mImageView.setImageURI(Uri.parse("file://$image"))
        }
    }

    override fun getItemCount(): Int {
        return mImageList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImageView: SimpleDraweeView = itemView.findViewById(R.id.image_item)

    }

}