package com.hefuwei.kdiycode.pages.splash

import android.os.Bundle
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.pages.login.LoginActivity
import com.hefuwei.kdiycode.pages.main.MainActivity

class SplashActivity: BaseActivity(), SplashContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter = SplashPresenter(this)
    }

    override fun enterNextPage(gotoLogin: Boolean) {
        if (gotoLogin) {
            LoginActivity.actionStart(this)
        } else {
            MainActivity.actionStart(this)
        }
        finish()
    }
}