package com.zhangwen.album.Bean

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object PhotoSelectedList {
    var selectedList = LinkedList<PhotoBean>()
    var pos2indexMap = HashMap<Int, Int>()
    var index2posMap = HashMap<Int, Int>()
    fun add(photoBean: PhotoBean) {
        selectedList.add(photoBean)
        //更新pos-index映射表,二者键值对相反
        pos2indexMap[photoBean.position] = selectedList.size
        index2posMap[selectedList.size] = photoBean.position

    }

    fun delete(photoBean: PhotoBean) {
        //不可直接调用，删除操作会失败，猜测remove方法在匹配时是比较对象的哈希值？
        //selectedList.remove(photoBean)
        var size = selectedList.size
        for (i in 0 until size) {
            if (selectedList[i].position == photoBean.position
                && selectedList[i].uri == photoBean.uri
            ) {
                selectedList.removeAt(i)
                //更新一下序号
                updateMap(photoBean.position)
                break;
            }
        }

    }

    //返回传入postion对应的index。返回值null即为未选中状态
    fun getCurIndex(pos: Int): Int? {
        return pos2indexMap[pos]

    }

    fun getAll(): ArrayList<PhotoBean> {
        var arr: ArrayList<PhotoBean> = ArrayList()
        var size = selectedList.size
        for (i in 0 until size) {
            arr.add(selectedList[i])
        }
        return arr
    }

    private fun updateMap(pos: Int) {
        //获取要删除的index值
        var index = pos2indexMap[pos] as Int
        pos2indexMap.remove(pos)
        index2posMap.remove(index)
        index += 1
        if (index != null) {
            for (i in index..selectedList.size) {
                val pos = index2posMap[i] as Int
                val t = pos2indexMap[pos] as Int
                pos2indexMap[pos] = t - 1

            }
        }


    }

    fun size(): Int {
        return selectedList.size
    }
}

class PhotoBean(var position: Int, var uri: String)