package com.hefuwei.kdiycode.pages.main.fragment.news

import com.hefuwei.kdiycode.Main
import com.hefuwei.kdiycode.data.DataRepository
import com.hefuwei.kdiycode.data.model.News
import com.hefuwei.kdiycode.data.model.Node
import com.hefuwei.kdiycode.util.safelyDispose
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class NewsPresenter(val view: NewsContract.View) : NewsContract.Presenter {

    private var pageIndex = 0
    private var nodeId = 1
    private var disposables: ArrayList<Disposable?> = ArrayList()
    private lateinit var nodes: List<Node>
    val dataList = ArrayList<News>()

    override fun subscribe() {
        getNodesAndNewsList()
    }

    override fun unsubscribe() {
        disposables.safelyDispose()
    }

    private fun getNewsList() {
        disposables.add(DataRepository.newsList(nodeId, Main.PAGESIZE * pageIndex, Main.PAGESIZE)
                .subscribe({
                    // 如果是加载第一页那么就先清除list，不是第一页就是加载更多直接拼接
                    if (pageIndex == 0) {
                        dataList.clear()
                    }
                    dataList.addAll(it)
                    view.notifyDataSetChanged(it.size == Main.PAGESIZE)
                }, { view.notifyLoadFail() }))
    }

    private fun getNodesAndNewsList() {
        disposables.add(DataRepository.nodeList().flatMap(object : Function<List<Node>, ObservableSource<List<News>>> {
            override fun apply(nodeList: List<Node>): ObservableSource<List<News>> {
                nodes = nodeList
                nodeId = nodeList[0].id
                view.notifyNodesAcquire(nodes)
                return DataRepository.newsList(nodeId, Main.PAGESIZE * pageIndex, Main.PAGESIZE)
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataList.addAll(it)
                    view.notifyDataSetChanged(it.size == Main.PAGESIZE)
                }, { view.notifyLoadFail() }))
    }

    override fun onNodeChange(position: Int) {
        nodeId = nodes[position].id
        pageIndex = 0
        getNewsList()
    }

    override fun getUserInfo(position: Int) {
        disposables.add(DataRepository.userInfo(dataList[position].user.login!!)
                .subscribe { view.jumpToUserProfile(it) })
    }

    override fun onLoadMore() {
        pageIndex++
        getNewsList()
    }

    override fun onRefresh() {
        pageIndex = 0
        getNewsList()
    }

}