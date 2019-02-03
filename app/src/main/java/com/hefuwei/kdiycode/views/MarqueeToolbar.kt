package com.hefuwei.kdiycode.views

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar

/**
 * title支持走马灯的Toolbar
 */
class MarqueeToolbar : Toolbar {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun setTitle(title: CharSequence) {
        super.setTitle(title)
        // 只能通过反射拿
        try {
            val clazz = Class.forName("androidx.appcompat.widget.Toolbar")
            val field = clazz.getDeclaredField("mTitleTextView")
            field.isAccessible = true
            val textView = field.get(this) as AppCompatTextView
            textView.ellipsize = TextUtils.TruncateAt.MARQUEE
            textView.setSingleLine()
            textView.marqueeRepeatLimit = -1
            textView.setHorizontallyScrolling(true)
            textView.isFocusable = true
            textView.isFocusableInTouchMode = true
            // 这个属性得加上
            textView.isSelected = true
            textView.translationX = -60f
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}