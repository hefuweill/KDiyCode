package com.hefuwei.kdiycode.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.util.UIUtils

/**
 * 用户资料的Item
 */
class ProfileItem @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    @BindView(R.id.tv_title)
    lateinit var tvTitle: TextView
    @BindView(R.id.tv_content)
    lateinit var tvContent: TextView

    init {
        init()
        val array = context.obtainStyledAttributes(attrs, R.styleable.ProfileItem)
        tvTitle.text = array.getString(R.styleable.ProfileItem_iTitle)
        tvContent.text = array.getString(R.styleable.ProfileItem_iContent)
        array.recycle()
    }

    private fun init() {
        val padding = UIUtils.dp2px(15)
        setPadding(padding, padding, padding, padding)
        background = UIUtils.getDrawable(R.drawable.select_ripple)
        View.inflate(context, R.layout.item_profile, this)
        ButterKnife.bind(this)
    }
}