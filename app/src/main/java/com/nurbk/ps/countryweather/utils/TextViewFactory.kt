package com.nurbk.ps.countryweather.utils

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.annotation.StyleRes

class TextViewFactory(
    private val context: Context,
    @field:StyleRes @param:StyleRes private val styleId: Int,
    private val center: Boolean
) :
    ViewSwitcher.ViewFactory {
    override fun makeView(): View {
        val textView = TextView(context)
        if (center) {
            textView.gravity = Gravity.CENTER
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(context, styleId)
        } else {
            textView.setTextAppearance(styleId)
        }
        return textView
    }
}
