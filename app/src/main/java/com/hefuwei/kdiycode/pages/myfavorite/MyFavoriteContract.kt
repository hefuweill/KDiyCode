package com.hefuwei.kdiycode.pages.myfavorite

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView

interface MyFavoriteContract {

    interface View : BaseView<Presenter> {
        fun notifyDataSetChanged()
        fun notifyDeleteResult(isSuccess: Boolean)
    }

    interface Presenter : BasePresenter {
        fun deleteFavorite(position: Int)
    }
}