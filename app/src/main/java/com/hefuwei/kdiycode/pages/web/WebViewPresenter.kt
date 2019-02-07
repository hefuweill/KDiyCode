package com.hefuwei.kdiycode.pages.web

import com.hefuwei.kdiycode.data.DataRepository
import com.hefuwei.kdiycode.util.safelyDispose
import io.reactivex.disposables.Disposable

class WebViewPresenter(val view: WebViewContract.View): WebViewContract.Presenter {

    private var disposable: Disposable? = null

    override fun addMyFavorite(title: String, url: String) {
        disposable = DataRepository.addMyFavorite(title, url)
                .subscribe({ view.onAddFavoriteSuccess() }, {})
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
        disposable.safelyDispose()
    }

}