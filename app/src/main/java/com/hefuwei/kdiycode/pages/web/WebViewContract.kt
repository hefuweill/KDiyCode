package com.hefuwei.kdiycode.pages.web

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView

interface WebViewContract {

    interface View : BaseView<Presenter> {
        fun onAddFavoriteSuccess()
    }

    interface Presenter : BasePresenter {
        fun addMyFavorite(title: String, url: String)
    }
}