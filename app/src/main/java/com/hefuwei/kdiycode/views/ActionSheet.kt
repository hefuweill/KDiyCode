package com.hefuwei.kdiycode.views

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.Window
import com.hefuwei.kdiycode.R
import java.util.*


class ActionSheet(ctx: Context): Dialog(ctx) {

    init {
        val window: Window = Objects.requireNonNull(window)!!
        window.setGravity(Gravity.BOTTOM)
        window.setWindowAnimations(R.style.ActionSheet)
        window.decorView.setBackgroundColor(Color.WHITE)
        window.decorView.setPadding(0, 0, 0, 0)
        val params = window.attributes
        params.width = -1
        window.attributes = params
    }
}