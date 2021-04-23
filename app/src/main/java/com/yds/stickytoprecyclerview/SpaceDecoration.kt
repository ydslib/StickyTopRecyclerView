package com.yds.stickytoprecyclerview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.*
import com.chad.library.adapter.base.BaseQuickAdapter

class SpaceDecoration : RecyclerView.ItemDecoration {
    var space = 0
    var headerCount = -1
    var footerCount = Int.MAX_VALUE
    var mPaddingEdgeSide = true
    var mPaddingStart = true
    var mPaddingHeaderFooter = false
    var mColorDrawable: ColorDrawable? = null

    var mDrawLastItem = true
    var mDrawHeaderFooter = false

    constructor(space: Int) {
        mColorDrawable = ColorDrawable(Color.parseColor("#e7e7e7"))
        this.space = space
    }

    constructor(space: Int, color: Int) {
        mColorDrawable = ColorDrawable(color)
        this.space = space
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        var spanCount = 0
        var orientation = 0
        var spanIndex = 0
        var headerCount = 0
        var footerCount = 0
        if (parent.adapter is BaseQuickAdapter<*, *>) {
            headerCount = (parent.adapter as BaseQuickAdapter<*, *>?)!!.headerLayoutCount
            footerCount = (parent.adapter as BaseQuickAdapter<*, *>?)!!.footerLayoutCount
        }

        val layoutManager = parent.layoutManager
        if (layoutManager is StaggeredGridLayoutManager) {
            orientation = (layoutManager as StaggeredGridLayoutManager?)!!.orientation
            spanCount = (layoutManager as StaggeredGridLayoutManager?)!!.spanCount
            spanIndex =
                (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).getSpanIndex()
        } else if (layoutManager is GridLayoutManager) {
            orientation = (layoutManager as GridLayoutManager?)!!.orientation
            spanCount = (layoutManager as GridLayoutManager?)!!.spanCount
            spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
        } else if (layoutManager is LinearLayoutManager) {
            orientation = (layoutManager as LinearLayoutManager?)!!.getOrientation()
            spanCount = 1
            spanIndex = 0
        }

        /**
         * 普通Item的尺寸
         */
        /**
         * 普通Item的尺寸
         */
        if (position >= headerCount && position < parent.adapter!!.itemCount - footerCount) {
            if (orientation == LinearLayout.VERTICAL) {
                val expectedWidth =
                    (parent.width - space * (spanCount + if (mPaddingEdgeSide) 1 else -1)).toFloat() / spanCount
                val originWidth = parent.width.toFloat() / spanCount
                val expectedX =
                    (if (mPaddingEdgeSide) space else 0) + (expectedWidth + space) * spanIndex
                val originX = originWidth * spanIndex
                outRect.left = (expectedX - originX).toInt()
                outRect.right = (originWidth - outRect.left - expectedWidth).toInt()
                if (position - headerCount < spanCount && mPaddingStart) {
                    outRect.top = space
                }
                outRect.bottom = space
                return
            } else {
                val expectedHeight =
                    (parent.height - space * (spanCount + if (mPaddingEdgeSide) 1 else -1)).toFloat() / spanCount
                val originHeight = parent.height.toFloat() / spanCount
                val expectedY =
                    (if (mPaddingEdgeSide) space else 0) + (expectedHeight + space) * spanIndex
                val originY = originHeight * spanIndex
                outRect.bottom = (expectedY - originY).toInt()
                outRect.top = (originHeight - outRect.bottom - expectedHeight).toInt()
                if (position - headerCount < spanCount && mPaddingStart) {
                    outRect.left = space
                }
                outRect.right = space
                return
            }
        } else if (mPaddingHeaderFooter) {
            if (orientation == LinearLayout.VERTICAL) {
                outRect.left = if (mPaddingEdgeSide) space else 0
                outRect.right = outRect.left
                outRect.top = if (mPaddingStart) space else 0
            } else {
                outRect.bottom = if (mPaddingEdgeSide) space else 0
                outRect.top = outRect.bottom
                outRect.left = if (mPaddingStart) space else 0
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter == null) {
            return
        }
        var orientation = 0
        var headerCount = 0
        var footerCount = 0
        val dataCount: Int
        if (parent.adapter is BaseQuickAdapter<*, *>) {
            headerCount = (parent.adapter as BaseQuickAdapter<*, *>?)!!.headerLayoutCount
            footerCount = (parent.adapter as BaseQuickAdapter<*, *>?)!!.footerLayoutCount
            dataCount = (parent.adapter as BaseQuickAdapter<*, *>?)!!.itemCount
        } else {
            dataCount = parent.adapter!!.itemCount
        }
        val dataStartPosition = headerCount
        val dataEndPosition = headerCount + dataCount
        val layoutManager = parent.layoutManager
        if (layoutManager is StaggeredGridLayoutManager) {
            orientation = layoutManager.orientation
        } else if (layoutManager is GridLayoutManager) {
            orientation = layoutManager.orientation
        } else if (layoutManager is LinearLayoutManager) {
            orientation = layoutManager.orientation
        }
        val start: Int
        val end: Int
        if (orientation == OrientationHelper.VERTICAL) {
            start = parent.paddingLeft
            end = parent.width - parent.paddingRight
        } else {
            start = parent.paddingTop
            end = parent.height - parent.paddingBottom
        }
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            if (position >= dataStartPosition && position < dataEndPosition - 1 //数据项除了最后一项
                || position == dataEndPosition - 1 && mDrawLastItem //数据项最后一项
                || !(position >= dataStartPosition && position < dataEndPosition) && mDrawHeaderFooter //header&footer且可绘制
            ) {
                if (orientation == OrientationHelper.VERTICAL) {
                    val params = child.layoutParams as RecyclerView.LayoutParams
                    val top = child.bottom + params.bottomMargin
                    mColorDrawable!!.setBounds(start, top, end, top)
                    mColorDrawable!!.draw(c!!)
                } else {
                    val params = child.layoutParams as RecyclerView.LayoutParams
                    val left = child.right + params.rightMargin
                    mColorDrawable!!.setBounds(left, start, left, end)
                    mColorDrawable!!.draw(c!!)
                }
            }
        }
    }

}