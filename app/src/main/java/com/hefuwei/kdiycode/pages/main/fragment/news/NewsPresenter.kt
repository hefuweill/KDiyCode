package com.hefuwei.kdiycode.pages.main.fragment.news

import com.hefuwei.kdiycode.Main
import com.hefuwei.kdiycode.data.DataRepository
import com.hefuwei.kdiycode.data.model.News
import com.hefuwei.kdiycode.pages.user.UserProfileActivity
import com.hefuwei.kdiycode.util.safelyDispose
import io.reactivex.disposables.Disposable

class NewsPresenter(val view: NewsContract.View) : NewsContract.Presenter {

    private var pageIndex = 0
    private var nodeId = 1
    private var disposable: Disposable? = null
    val dataList = ArrayList<News>()

    override fun subscribe() {
        getNewsList()
    }

    override fun unsubscribe() {
        disposable.safelyDispose()
    }

    private fun getNewsList() {
        disposable = DataRepository.newsList(nodeId, Main.PAGESIZE * pageIndex, Main.PAGESIZE)
                .subscribe({
                    // 如果是加载第一页那么就先清除list，不是第一页就是加载更多直接拼接
                    if (pageIndex == 0) {
                        dataList.clear()
                    }
                    dataList.addAll(it)
                    view.notifyDataSetChanged()
                }, {})
    }

    override fun getUserInfo(position: Int) {
        disposable = DataRepository.userInfo(dataList[position].user.login!!)
                .subscribe { view.jumpToUserProfile(it) }
    }

}