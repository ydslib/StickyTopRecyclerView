package com.yds.stickytoprecyclerview

import com.chad.library.adapter.base.entity.MultiItemEntity

class PictureModel(var type: Int) :MultiItemEntity{

    companion object{
        const val PICTURE_TITLE = 1
        const val PICTURE_CONTENT = 0
    }

     var id: String? = null
     var title: String? = null
     var date_time: String? = null
     var create_time: String? = null
     var picture_count: String? = null
     var status: String? = null
     var cover_image: String? = null
     var date: String? = null
    override fun getItemType(): Int {
        return type
    }

//    override val itemType: Int
//        get() = type


}