package com.hefuwei.kdiycode.pages.main.fragment.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseFragment
import com.hefuwei.kdiycode.data.model.NodeModel
import com.hefuwei.kdiycode.data.model.UserInfoModel
import com.hefuwei.kdiycode.pages.user.UserProfileActivity
import com.hefuwei.kdiycode.pages.web.WebViewActivity
import com.hefuwei.kdiycode.util.UIUtils
import com.hefuwei.kdiycode.views.ChooseNodeView

class NewsFragment: BaseFragment(), NewsContract.View {

    @BindView(R.id.rv)
    lateinit var rv: RecyclerView
    @BindView(R.id.sr)
    lateinit var sr: SwipeRefreshLayout
    private lateinit var tvError: TextView
    private lateinit var adapter: NewsAdapter
    private lateinit var newsPresenter: NewsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_news, container, false)
        presenter = NewsPresenter(this)
        newsPresenter = presenter as NewsPresenter
        return rootView
    }

    override fun setupView() {
        super.setupView()
        adapter = NewsAdapter(R.layout.item_news, (presenter as NewsPresenter).dataList)
        adapter.emptyView = View.inflate(context, R.layout.pager_error, null)
        adapter.emptyView.visibility = View.INVISIBLE
        tvError = adapter.emptyView.findViewById(R.id.tv_error)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
        // 进入页面是时候先要是再刷新的
        sr.isRefreshing = true
    }

    override fun setupEvent() {
        super.setupEvent()
        tvError.setOnClickListener { newsPresenter.onRefresh() }
        sr.setOnRefreshListener { newsPresenter.onRefresh() }
        adapter.onItemChildClickListener = object : OnItemChildClickListener(), BaseQuickAdapter.OnItemChildClickListener {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                newsPresenter.getUserInfo(position)
            }
        }
        adapter.onItemClickListener = object : OnItemClickListener(), BaseQuickAdapter.OnItemClickListener {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                jumpToWebView((presenter as NewsPresenter).dataList[position].address!!)
            }
        }
        adapter.setOnLoadMoreListener({ newsPresenter.onLoadMore() }, rv)
    }

    override fun jumpToUserProfile(info: UserInfoModel) {
        UserProfileActivity.actionStart(context!!, info)
    }

    private fun jumpToWebView(url: String) {
        WebViewActivity.actionStart(context!!, url)
    }

    override fun notifyDataSetChanged(hasMore: Boolean) {
        sr.isRefreshing = false
        adapter.notifyDataSetChanged()
        if (hasMore) {
            adapter.loadMoreComplete()
        } else {
            adapter.loadMoreEnd()
        }
    }

    override fun notifyLoadFail() {
        sr.isRefreshing = false
        adapter.emptyView.visibility = View.VISIBLE
        adapter.loadMoreFail()
        UIUtils.showShortToast(R.string.load_fail)
    }

    override fun notifyNodesAcquire(nodes: List<NodeModel>) {
        val headerView = ChooseNodeView(context!!, nodes)
        headerView.setOnTagClickListener(object : ChooseNodeView.OnTagClickListener{
            override fun onTagClick(position: Int) {
                newsPresenter.onNodeChange(position)
            }
        })
        adapter.addHeaderView(headerView)
    }

}