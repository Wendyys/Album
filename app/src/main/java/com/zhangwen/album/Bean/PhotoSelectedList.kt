package com.zhangwen.album.Bean

import java.util.*
import kotlin.collections.ArrayList

object PhotoSelectedList {
    var selectedList = LinkedList<PhotoBean>()
    fun delete(photoBean: PhotoBean) {
        //不可直接调用remove方法，删除操作会失败，猜测remove方法在匹配时是比较对象的哈希值？
        //selectedList.remove(photoBean)
        val size = selectedList.size
        for (i in 0 until size) {
            if (selectedList[i].position == photoBean.position
                && selectedList[i].uri == photoBean.uri
            ) {
                //将index改成-1
                selectedList[i].index = -1
                selectedList.removeAt(i)
                //更新一下index
                updateMap()
                break;
            }
        }

    }

    fun get(pos: Int): PhotoBean {
        return selectedList[pos]
    }

    fun getAll(): ArrayList<PhotoBean> {
        val arr: ArrayList<PhotoBean> = ArrayList()
        val size = selectedList.size
        for (i in 0 until size) {
            arr.add(selectedList[i])
        }
        return arr
    }

    fun clear() {
        selectedList.clear()
    }

    private fun updateMap() {
        //由于selectedList是个链表结构，因此将index重置为在当前列表中的顺序
        for (i in 0 until selectedList.size) {
            selectedList[i].index = i + 1
        }
    }

    fun size(): Int {
        return selectedList.size
    }
}

