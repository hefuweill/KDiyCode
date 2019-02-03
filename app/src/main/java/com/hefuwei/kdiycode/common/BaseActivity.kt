package com.hefuwei.kdiycode.common

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hefuwei.kdiycode.util.ActivityCollector
import java.lang.ref.WeakReference

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private var weakRefActivity: WeakReference<Activity>? = null
    protected var presenter: BasePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakRefActivity = WeakReference(this)
        ActivityCollector.add(weakRefActivity)
    }

    override fun onStart() {
        super.onStart()
        presenter?.subscribe()
        setupViews()
        setupToolbar()
        setupEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unsubscribe()
        ActivityCollector.remove(weakRefActivity)
    }

    protected open fun setupViews() {}

    protected open fun setupEvent() {}

    protected open fun setupToolbar() {}

}