package com.hefuwei.kdiycode.pages.login

import com.hefuwei.kdiycode.Auth
import com.hefuwei.kdiycode.DiyCode
import com.hefuwei.kdiycode.data.DataRepository
import com.hefuwei.kdiycode.util.ShareUtils
import com.hefuwei.kdiycode.util.safelyDispose
import io.reactivex.disposables.Disposable

class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {

    private var disposables: ArrayList<Disposable?> = ArrayList()

    override fun subscribe() {
    }

    override fun unsubscribe() {
        disposables.safelyDispose()
    }

    override fun login(name: String, password: String) {
        if (name.isEmpty()) {
            view.showNameError()
            return
        } else if (password.isEmpty()) {
            view.showPasswordError()
            return
        }
        disposables.add(DataRepository.login(name, password)
                .subscribe({
                    ShareUtils.save(Auth.CREATE_AT, it.created_at)
                    ShareUtils.save(Auth.EXPIRES_IN, it.expires_in)
                    ShareUtils.save(Auth.TOKEN, it.access_token)
                    ShareUtils.save(Auth.UID, it.uid)
                    DiyCode.refreshLoginState()
                    saveLoginUserInfo(name, password, it.uid)
                    view.enterNextPage()
                }, { view.onLoginFail(it.message) }))
    }

    private fun saveLoginUserInfo(name: String, password: String, uid: Int) {
        disposables.add(DataRepository.saveLoginUserInfo(name, password, uid)
                .subscribe({}, {}))
    }
}