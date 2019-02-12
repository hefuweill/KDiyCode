package com.hefuwei.kdiycode.pages.main.fragment.projects

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView
import com.hefuwei.kdiycode.data.model.NodeModel
import com.hefuwei.kdiycode.data.model.UserInfoModel

interface SitesContract {

    interface View : BaseView<Presenter> {
        fun notifyDataSetChanged()
        fun notifyDataLoadFail()
    }

    interface Presenter : BasePresenter {
        fun onRefresh()
    }
}