package com.hefuwei.kdiycode.pages.choosenode

import com.hefuwei.kdiycode.data.DataRepository
import com.hefuwei.kdiycode.util.safelyDispose
import io.reactivex.disposables.Disposable

class ChooseNodePresenter(val view: ChooseNodeContract.View) : ChooseNodeContract.Presenter {

    private var disposable: Disposable? = null

    override fun subscribe() {
        getNodes()
    }

    override fun unsubscribe() {
        disposable.safelyDispose()
    }

    private fun getNodes() {
        disposable = DataRepository.nodeList()
                .subscribe({ view.notifyLoadNodeListSuccess(it) }, { view.notifyLoadNodeListFail() })
    }

}