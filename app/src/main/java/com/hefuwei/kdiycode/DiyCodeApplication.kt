package com.hefuwei.kdiycode

import android.app.Application

class DiyCodeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DiyCode.initialize(this)
    }
}