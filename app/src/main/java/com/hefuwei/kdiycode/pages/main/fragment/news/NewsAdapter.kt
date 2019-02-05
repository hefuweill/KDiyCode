package com.hefuwei.kdiycode.pages.main.fragment.news

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.data.model.NewsModel
import com.hefuwei.kdiycode.util.UIUtils

/**
 * 使用开源框架 https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
class NewsAdapter(resId: Int, newsList: ArrayList<NewsModel>) :
        BaseQuickAdapter<NewsModel, BaseViewHolder>(resId, newsList) {

    override fun convert(helper: BaseViewHolder, item: NewsModel) {
        helper.setText(R.id.tv_user, item.user.name)
                .setText(R.id.tv_title, item.title)
                .setText(R.id.tv_nodeName, item.node_name)
                .setText(R.id.tv_host, UIUtils.getHost(item.address!!))
                .setText(R.id.tv_time, UIUtils.formatTime(item.   updated_at!!))
                .addOnClickListener(R.id.iv)
        val imageView = helper.getView<ImageView>(R.id.iv)
        Glide.with(imageView)
                .load(UIUtils.replaceLargeAvatarToAvatar(item.user.avatar_url!!))
                .into(imageView)
    }

}