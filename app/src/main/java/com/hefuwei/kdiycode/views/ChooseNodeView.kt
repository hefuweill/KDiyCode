package com.hefuwei.kdiycode.views

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.data.model.NodeModel
import com.hefuwei.kdiycode.util.LogUtils
import com.hefuwei.kdiycode.util.UIUtils

/**
 * 选择node的自定义ViewGroup
 */
class ChooseNodeView(context: Context, private val mDataSource: List<NodeModel>) : ViewGroup(context), View.OnClickListener {

    private var mCurrentSelect = 0
    private var mListener: OnTagClickListener? = null

    init {
        for (i in mDataSource.indices) {
            val node = mDataSource[i]
            val tag = TagView(getContext())
            tag.text = node.name
            tag.textSize = UIUtils.dp2px(5).toFloat()
            tag.setTextColor(Color.WHITE)
            tag.setBorderColor(Color.WHITE)
            tag.setOnClickListener(this)
            if (i == mCurrentSelect) {
                tag.setBGColor(UIUtils.getColor(R.color.tag_selected))
            } else {
                tag.setBGColor(UIUtils.getColor(R.color.tag_normal))
            }
            addView(tag)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentLeft = PADDING_HOR
        var currentTop = PADDING_VER
        var maxHeight = 0
        for (i in mDataSource.indices) {
            val childView = getChildAt(i)
            maxHeight = Math.max(maxHeight, childView.measuredHeight)
            if (currentLeft + childView.measuredWidth + PADDING_HOR >= measuredWidth) {
                // 换行操作
                currentLeft = PADDING_HOR
                currentTop += maxHeight + PADDING_VER
                maxHeight = 0
            }
            childView.layout(currentLeft, currentTop, currentLeft + childView.measuredWidth,
                    currentTop + childView.measuredHeight)
            LogUtils.d("left = " + currentLeft + " top = " + currentTop + " right = " +
                    (currentLeft + childView.measuredWidth) + " bottom = " + (currentTop + measuredHeight))
            currentLeft += childView.measuredWidth + PADDING_HOR
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        // 当前的left值
        var currentLeft = PADDING_HOR
        // 总共需要的高度
        var totalHeight = PADDING_VER
        // 每一行的最大高度
        var maxHeight = 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            maxHeight = Math.max(maxHeight, childView.measuredHeight)
            if (currentLeft + childView.measuredWidth + PADDING_HOR >= width) {
                // 换行操作
                totalHeight += maxHeight + PADDING_VER
                maxHeight = 0
                currentLeft = PADDING_HOR
            }
            currentLeft += childView.measuredWidth + PADDING_HOR
        }
        // 把最后一行的高度加上
        totalHeight += maxHeight + PADDING_VER
        setMeasuredDimension(width, totalHeight)
    }

    override fun onClick(v: View) {
        for (i in 0 until childCount) {
            if (getChildAt(i) === v) {
                changeSelectedItem(i)
                mCurrentSelect = i
                if (mListener != null) {
                    mListener!!.onTagClick(i)
                }
                return
            }
        }
    }

    private fun changeSelectedItem(newPosition: Int) {
        val oldTag = getChildAt(mCurrentSelect) as TagView
        oldTag.setBGColor(UIUtils.getColor(R.color.tag_normal))

        val newTag = getChildAt(newPosition) as TagView
        newTag.setBGColor(UIUtils.getColor(R.color.tag_selected))
    }

    interface OnTagClickListener {

        fun onTagClick(position: Int)
    }

    fun setOnTagClickListener(listener: OnTagClickListener) {
        mListener = listener
    }

    companion object {
        private const val PADDING_HOR = 50
        private const val PADDING_VER = 30
    }
}