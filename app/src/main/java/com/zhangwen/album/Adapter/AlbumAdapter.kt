package com.zhangwen.album.Adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.zhangwen.album.Bean.PhotoSelectedList
import com.zhangwen.album.OnItemClickListener
import com.zhangwen.album.R

class AlbumAdapter(
    private val mImageList: ArrayList<String>,
    private val mContext: Context,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
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
        if(bindIndex(position) != -1){
            Log.d(TAG,position.toString())
            holder.mPickButton.isChecked = true
            holder.mPickButton.text = bindIndex(position).toString()
        }else{
            //要取消一下状态啊姐  要不然滑动一下会发现很多pickButton被莫名其妙的设置了数字
            holder.mPickButton.isChecked = false
            holder.mPickButton.text = ""
        }
    }

    override fun getItemCount(): Int {
        return mImageList.size
    }

    /**
     *  由于viewholder存在复用情况，不能简单的拿toggle button去绑定数字
     *  用hashmap检测是否命中已保存的图片index，时间复杂度为O(1)
     *  @param 当前图片的index
     *  @return 应该绑定的pos(1-9) 返回-1即为未选中状态
     */
    private fun bindIndex(pos: Int): Int {
        return PhotoSelectedList.getCurIndex(pos) ?: -1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImageView: SimpleDraweeView = itemView.findViewById(R.id.image_item)
        var mPickButton: ToggleButton = itemView.findViewById(R.id.image_pick)

        init {
            mImageView.setOnClickListener(View.OnClickListener {
                onItemClickListener?.onPhotoClick(it, adapterPosition, mImageList[adapterPosition])
            })
            mPickButton.setOnClickListener(View.OnClickListener {
                onItemClickListener?.onToggleClick(
                    it,
                    adapterPosition,
                    mImageList[adapterPosition],
                    mPickButton.isChecked
                )
            })
        }

    }

}