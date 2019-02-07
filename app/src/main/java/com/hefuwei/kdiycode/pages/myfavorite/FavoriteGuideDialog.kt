package com.hefuwei.kdiycode.pages.myfavorite

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.graphics.Bitmap
import android.graphics.Color
import android.view.Gravity
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import com.hefuwei.kdiycode.R
import java.util.*


/**
 * 引导用户使用，我的收藏功能
 */
class FavoriteGuideDialog(private val ctx: Context, val view: View): Dialog(ctx) {

    override fun show() {
        val window: Window = Objects.requireNonNull(window)!!
        val imageView = ImageView(context)
        imageView.setImageBitmap(getViewBitmap(view))
        window.decorView.setBackgroundColor(Color.TRANSPARENT)
        window.decorView.setPadding(0, 0, 0, 0)
        val params = window.attributes
        val array = IntArray(2) { 0 }
        view.getLocationOnScreen(array)
        val (left, top) = array
        params.y = top - getStatusBarHeight()
        params.x = left
        params.gravity = Gravity.LEFT or Gravity.TOP
        window.attributes = params
        val rootView = View.inflate(context, R.layout.dialog_guide_favorite, null) as ViewGroup
        rootView.addView(imageView, 0)
        setContentView(rootView)
        super.show()
    }

    private fun getViewBitmap(view: View): Bitmap {
        val widthSpec = MeasureSpec.makeMeasureSpec(view.width, MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(view.height, MeasureSpec.EXACTLY)
        view.measure(widthSpec, heightSpec)
        view.layout(0, 0, view.width, view.height)
        view.buildDrawingCache(false)
        return view.getDrawingCache(false)
    }

    private fun getStatusBarHeight(): Int {
        val activity = ctx as Activity
        return (activity.window.decorView as ViewGroup).getChildAt(1).height
    }
}