package com.hefuwei.kdiycode

import android.app.Application
import com.hefuwei.kdiycode.util.CrashHandler
import org.litepal.LitePal

class DiyCodeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CrashHandler.initialize(this)
        DiyCode.initialize(this)
        LitePal.initialize(this)
    }
}