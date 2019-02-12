package com.hefuwei.kdiycode.pages.main.fragment.sites

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.data.model.SectionEntityModel

/**
 * 使用开源框架 https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
class SitesAdapter(resId: Int, headerResId: Int, siteList: ArrayList<SectionEntityModel>) :
        BaseSectionQuickAdapter<SectionEntityModel, BaseViewHolder>(resId, headerResId, siteList) {

    override fun convert(helper: BaseViewHolder?, item: SectionEntityModel?) {
        if (item?.t != null && helper != null) {
            helper.setText(R.id.tv_title, item.t.name)
            val iv = helper.getView<ImageView>(R.id.iv)
            Glide.with(iv)
                    .load(item.t.avatar_url)
                    .into(iv)
        }
    }

    override fun convertHead(helper: BaseViewHolder?, item: SectionEntityModel?) {
        if (item?.header != null && helper != null) {
            helper.setText(R.id.tv, item.header)
        }
    }

}