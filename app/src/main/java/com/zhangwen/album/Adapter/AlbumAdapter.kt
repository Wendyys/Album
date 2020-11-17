package com.zhangwen.album.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.zhangwen.album.OnItemClickListener
import com.zhangwen.album.R

class AlbumAdapter(private val mImageList: ArrayList<String>, private val mContext: Context,private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>(){
    private val TAG: String = "Album"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
        var mPickButton :ToggleButton = itemView.findViewById(R.id.image_pick)
        init {
            mImageView.setOnClickListener(View.OnClickListener {
                onItemClickListener?.onPhotoClick(it,adapterPosition)
            })
            mPickButton.setOnClickListener(View.OnClickListener {
                onItemClickListener?.onToggleClick(it,adapterPosition)
            })
        }

    }

}