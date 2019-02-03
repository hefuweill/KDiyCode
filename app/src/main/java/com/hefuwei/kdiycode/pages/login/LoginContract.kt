package com.hefuwei.kdiycode.pages.login

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun enterNextPage()
        fun showNameError()
        fun showPasswordError()
        fun onLoginFail(msg: String?)
    }

    interface Presenter : BasePresenter {
        fun login(name: String, password: String)
    }
}