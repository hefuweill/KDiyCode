package com.hefuwei.kdiycode.pages.main

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView
import com.hefuwei.kdiycode.data.model.UserInfoModel

interface MainContract {

    interface View : BaseView<Presenter> {
        fun notifyMeInfoAcquire(info: UserInfoModel)
        fun notifyAcquireMeInfoFail()
        fun notifyCreateNewsSuccess()
        fun notifyCreateNewsFail()
    }

    interface Presenter : BasePresenter {
        fun createNews(title: String, address: String, id: Int)
    }
}