package com.zhangwen.album.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.zhangwen.album.Bean.PhotoBean
import com.zhangwen.album.OnThumbItemClickListener
import com.zhangwen.album.R

//预览页面 略缩图list的adapter
class PreviewThumbAdapter(
    private val mContext: Context,
    var mList: ArrayList<PhotoBean>,
    private val onThumbItemClickListener: OnThumbItemClickListener
) :
    RecyclerView.Adapter<PreviewThumbAdapter.ThumbViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbViewHolder {
        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.preview_thumb_list_item, parent, false)
        return ThumbViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ThumbViewHolder, position: Int) {
        val imageUri = mList[position].uri
        holder.imageView.setImageURI(Uri.parse("file://$imageUri"))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ThumbViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: SimpleDraweeView = itemView.findViewById<SimpleDraweeView>(R.id.preview_thumb_item)

        init {
            imageView.setOnClickListener(View.OnClickListener {
                onThumbItemClickListener?.OnItemClickListener()
            })
        }
    }
}