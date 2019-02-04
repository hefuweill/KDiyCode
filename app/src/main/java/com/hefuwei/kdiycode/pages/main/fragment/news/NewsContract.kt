package com.hefuwei.kdiycode.pages.main.fragment.news

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView
import com.hefuwei.kdiycode.data.model.Node
import com.hefuwei.kdiycode.data.model.UserInfoModel

interface NewsContract {

    interface View : BaseView<Presenter> {
        fun notifyDataSetChanged(hasMore: Boolean)
        fun notifyNodesAcquire(nodes: List<Node>)
        fun notifyLoadFail()
        fun jumpToUserProfile(info: UserInfoModel)
    }

    interface Presenter : BasePresenter {
        fun getUserInfo(position: Int)
        fun onLoadMore()
        fun onRefresh()
        fun onNodeChange(position: Int)
    }
}