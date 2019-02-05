package com.hefuwei.kdiycode.pages.main

import com.hefuwei.kdiycode.data.DataRepository
import com.hefuwei.kdiycode.util.safelyDispose
import io.reactivex.disposables.Disposable

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {

    private var disposables: ArrayList<Disposable?> = ArrayList()

    override fun subscribe() {
        getMeInfo()
    }

    override fun unsubscribe() {
        disposables.safelyDispose()
    }

    override fun createNews(title: String, address: String, id: Int) {
        disposables.add(DataRepository.createNews(title, address, id)
                .subscribe({ view.notifyCreateNewsSuccess() }, { view.notifyCreateNewsFail() }))
    }

    private fun getMeInfo() {
        disposables.add(DataRepository.me()
                .subscribe({ view.notifyMeInfoAcquire(it) }, { view.notifyAcquireMeInfoFail() }))
    }

}