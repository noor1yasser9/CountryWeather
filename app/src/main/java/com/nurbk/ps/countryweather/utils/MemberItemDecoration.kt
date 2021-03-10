package com.nurbk.ps.countryweather.utils

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MemberItemDecoration(val mDivider: Drawable) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        val left = parent.paddingLeft
//        val right = parent.width - parent.paddingRight
//
//        val childCount = parent.childCount
//        for (i in 0 until childCount) {
//            val child = parent.getChildAt(i)
//            val params = child.layoutParams as RecyclerView.LayoutParams
//            val top = child.bottom + params.bottomMargin
//            val bottom = top + mDivider.intrinsicHeight
//            mDivider.setBounds(left, top, right, bottom)
//            mDivider.draw(Canvas(BitmapFactory.decodeResource(parent.context.resources)))
//        }
    }
}