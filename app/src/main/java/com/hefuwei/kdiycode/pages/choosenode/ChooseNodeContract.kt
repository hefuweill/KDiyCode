package com.hefuwei.kdiycode.pages.choosenode

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView
import com.hefuwei.kdiycode.data.model.NodeModel

interface ChooseNodeContract {

    interface View : BaseView<Presenter> {

        fun notifyLoadNodeListSuccess(nodes: List<NodeModel>)
        fun notifyLoadNodeListFail()
    }

    interface Presenter : BasePresenter
}