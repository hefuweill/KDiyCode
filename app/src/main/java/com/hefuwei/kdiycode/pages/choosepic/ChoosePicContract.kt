package com.hefuwei.kdiycode.pages.choosepic

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView

interface ChoosePicContract {

    interface View : BaseView<Presenter> {
        fun notifyDataSetChanged()
    }

    interface Presenter : BasePresenter {
        fun changeDir(path: String)
    }
}