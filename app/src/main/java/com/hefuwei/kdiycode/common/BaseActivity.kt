package com.hefuwei.kdiycode.common

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.hefuwei.kdiycode.util.ActivityCollector
import java.lang.ref.WeakReference

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    /**
     * 确保setupView等只会被执行一次
     */
    private var isCreated: Boolean = false
    private var weakRefActivity: WeakReference<Activity>? = null
    protected var presenter: BasePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakRefActivity = WeakReference(this)
        ActivityCollector.add(weakRefActivity)
    }

    override fun onStart() {
        super.onStart()
        if (!isCreated) {
            ButterKnife.bind(this)
            presenter?.subscribe()
            setupViews()
            setupToolbar()
            setupEvent()
            isCreated = true
        }
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