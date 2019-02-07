package com.hefuwei.kdiycode.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.util.UIUtils

/**
 * Tag
 */
class TagView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.TagView)
        val shapeColor = array.getColor(R.styleable.TagView_borderColor, Color.BLACK)
        val bg = GradientDrawable()
        bg.shape = GradientDrawable.RECTANGLE
        bg.cornerRadius = UIUtils.dp2px(4).toFloat()
        bg.setStroke(1, shapeColor)
        background = bg
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        setPadding(UIUtils.dp2px(8), 0, UIUtils.dp2px(8), 0)
        array.recycle()
    }

    fun setBGColor(color: Int) {
        val drawable = background as GradientDrawable
        drawable.setColor(color)
    }

    fun setBorderColor(color: Int) {
        val drawable = background as GradientDrawable
        drawable.setStroke(1, color)
    }
}