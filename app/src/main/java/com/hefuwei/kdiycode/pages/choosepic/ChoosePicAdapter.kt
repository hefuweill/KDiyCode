package com.hefuwei.kdiycode.pages.choosepic

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.data.model.ImageModel

class ChoosePicAdapter(resId: Int, imageList: List<ImageModel>) :
        BaseQuickAdapter<ImageModel, BaseViewHolder>(resId, imageList) {

    override fun convert(helper: BaseViewHolder?, item: ImageModel?) {
        if (helper != null && item != null) {
            val iv: ImageView = helper.getView(R.id.iv)
            Glide.with(iv).load(item.path)
                    .into(iv)
        }
    }
}