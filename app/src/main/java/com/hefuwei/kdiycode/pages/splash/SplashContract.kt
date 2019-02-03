package com.hefuwei.kdiycode.pages.splash

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView

interface SplashContract {

    interface View : BaseView<Presenter> {
        fun enterNextPage(gotoLogin: Boolean)
    }

    interface Presenter : BasePresenter
}