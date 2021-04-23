package com.yds.stickytoprecyclerview

import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class PictureAdapter(mData: ArrayList<PictureModel>) :
    BaseMultiItemQuickAdapter<PictureModel, BaseViewHolder>(mData) {

    init {
        addItemType(PictureModel.PICTURE_CONTENT, R.layout.item_pictures)
        addItemType(PictureModel.PICTURE_TITLE, R.layout.item_picture_month)
    }

    override fun convert(helper: BaseViewHolder, item: PictureModel) {

        if (helper.itemViewType === PictureModel.PICTURE_CONTENT) {
            //标题/数量
            helper.setText(
                R.id.tv_pictrue_title,
                item.title.toString() + "(" + item.picture_count + ")"
            )
            //时间
            helper.setText(R.id.tv_pictrue_time, item.date)
            //封面图
            Glide.with(mContext).load(item.cover_image).apply(RequestOptions().centerCrop())
                .into(helper.getView(R.id.iv_pictrue) as ImageView)
        } else if (helper.itemViewType === PictureModel.PICTURE_TITLE) {
            helper.setText(R.id.tv_picture_month, item.date)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        FullSpanUtil.onAttachedToRecyclerView(recyclerView, this, PictureModel.PICTURE_TITLE)
    }

    override fun onViewDetachedFromWindow(@NonNull holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder!!)
        FullSpanUtil.onViewAttachedToWindow(holder, this, PictureModel.PICTURE_TITLE)
    }
}