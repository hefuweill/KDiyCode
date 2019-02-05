package com.hefuwei.kdiycode.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.util.UIUtils

class InputItem (context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {


    @BindView(R.id.tv_title)
    lateinit var tvTitle: TextView
    @BindView(R.id.tv_required)
    lateinit var tvRequired: TextView
    @BindView(R.id.tv)
    lateinit var tv: TextView
    @BindView(R.id.et)
    lateinit var et: EditText
    @BindView(R.id.iv_rightArrow)
    lateinit var ivRightArrow: ImageView

    var content: String
        get() = et.text.toString()
        set(content) {
            tv.text = content
            et.setText(content)
        }

    init {
        init()
        val array = context.obtainStyledAttributes(attrs, R.styleable.InputItem)
        val required = array.getBoolean(R.styleable.InputItem_required, false)
        val showArrow = array.getBoolean(R.styleable.InputItem_showArrow, false)
        val title = array.getString(R.styleable.InputItem_title)
        val contentHint = array.getString(R.styleable.InputItem_contentHint)
        setRequired(required)
        setTitle(title)
        setContentHint(contentHint)
        setArrow(showArrow)
        array.recycle()
    }

    fun setFocus() {
        et.requestFocus()
    }

    private fun setRequired(required: Boolean) {
        if (required) {
            tvRequired.visibility = View.VISIBLE
        } else {
            tvRequired.visibility = View.GONE
        }
    }

    private fun setTitle(title: String?) {
        tvTitle.text = title
    }

    private fun setContentHint(hint: String?) {
        tv.hint = hint
    }

    private fun setArrow(showArrow: Boolean) {
        if (showArrow) {
            ivRightArrow.visibility = View.VISIBLE
            et.visibility = View.GONE
            tv.visibility = View.VISIBLE
        } else {
            ivRightArrow.visibility = View.GONE
            et.visibility = View.VISIBLE
            tv.visibility = View.GONE
        }
    }


    private fun init() {
        val padding = UIUtils.dp2px(20)
        setPadding(padding, padding, padding, padding)
        gravity = Gravity.CENTER
        View.inflate(context, R.layout.item_input, this)
        ButterKnife.bind(this)
    }
}