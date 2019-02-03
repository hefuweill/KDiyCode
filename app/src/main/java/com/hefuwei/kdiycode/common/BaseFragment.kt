package com.hefuwei.kdiycode.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import butterknife.ButterKnife

open class BaseFragment : Fragment() {

    protected lateinit var rootView: View
    protected lateinit var presenter: BasePresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
        setupEvent()
        loadDataFromServer()
        presenter.subscribe()
    }

    protected open fun setupView() {
        ButterKnife.bind(this, rootView)
    }

    protected open fun setupEvent() {

    }

    protected open fun loadDataFromServer() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }
}