package com.hefuwei.kdiycode.pages.splash

import android.os.SystemClock
import com.hefuwei.kdiycode.DiyCode
import com.hefuwei.kdiycode.data.DataRepository
import com.hefuwei.kdiycode.util.LogUtils
import com.hefuwei.kdiycode.util.safelyDispose
import io.reactivex.disposables.Disposable

class SplashPresenter(val view: SplashContract.View) : SplashContract.Presenter {

    private var disposable: Disposable? = null
    lateinit var timerThread: Thread

    /**
     * 如果用户已经登录了，那么进行网络请求，不然就等待两秒跳转到登录界面
     */
    override fun subscribe() {
        // 开启一个计时器线程，防止一直停留在闪屏页
        timerThread = Thread {
            try { Thread.sleep(5000); view.enterNextPage(!DiyCode.isLogin) } catch (e: InterruptedException) { }
        }
        timerThread.start()
        if (DiyCode.isLogin) {
            disposable = DataRepository.init()
                    .subscribe({ view.enterNextPage(gotoLogin = false) }, { LogUtils.e(it.message)} )
        } else {
            Thread { SystemClock.sleep(2000); view.enterNextPage(gotoLogin = true) }.start()
        }
    }

    override fun unsubscribe() {
        disposable.safelyDispose()
        timerThread.interrupt()
    }

}