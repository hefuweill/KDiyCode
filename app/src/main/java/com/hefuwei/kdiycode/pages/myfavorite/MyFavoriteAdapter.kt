package com.hefuwei.kdiycode.pages.myfavorite

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.data.model.DBMyFavoriteModel
import java.util.*

class MyFavoriteAdapter(resId: Int, favoriteList: List<DBMyFavoriteModel>):
        BaseQuickAdapter<DBMyFavoriteModel, BaseViewHolder>(resId, favoriteList) {

    override fun convert(helper: BaseViewHolder?, item: DBMyFavoriteModel?) {
        helper?.setText(R.id.tv_title, item?.title)
        helper?.setText(R.id.tv_time, "最后修改时间：${Date(item?.time!!).toLocaleString()}")
    }

}