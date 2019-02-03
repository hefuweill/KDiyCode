package com.hefuwei.kdiycode.pages.main.fragment.news

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView
import com.hefuwei.kdiycode.data.model.UserInfoModel

interface NewsContract {

    interface View : BaseView<Presenter> {
        fun notifyDataSetChanged()
        fun jumpToUserProfile(info: UserInfoModel)
    }

    interface Presenter : BasePresenter {
        fun getUserInfo(position: Int)
    }
}