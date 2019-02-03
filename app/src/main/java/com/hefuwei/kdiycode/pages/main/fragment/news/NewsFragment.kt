package com.hefuwei.kdiycode.pages.main.fragment.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseFragment
import com.hefuwei.kdiycode.data.model.UserInfoModel
import com.hefuwei.kdiycode.pages.user.UserProfileActivity
import com.hefuwei.kdiycode.pages.web.WebViewActivity

class NewsFragment: BaseFragment(), NewsContract.View {

    @BindView(R.id.rv)
    lateinit var rv: RecyclerView
    lateinit var adapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_news, container, false)
        presenter = NewsPresenter(this)
        return rootView
    }

    override fun setupView() {
        super.setupView()
        adapter = NewsAdapter(R.layout.item_news, (presenter as NewsPresenter).dataList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
    }

    override fun setupEvent() {
        super.setupEvent()
        adapter.onItemChildClickListener = object : OnItemChildClickListener(), BaseQuickAdapter.OnItemChildClickListener {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                (presenter as NewsPresenter).getUserInfo(position)
            }
        }
        adapter.onItemClickListener = object : OnItemClickListener(), BaseQuickAdapter.OnItemClickListener {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                jumpToWebView((presenter as NewsPresenter).dataList[position].address!!)
            }
        }
    }

    override fun jumpToUserProfile(info: UserInfoModel) {
        UserProfileActivity.actionStart(context!!, info)
    }

    override fun notifyDataSetChanged() {
        rv.adapter?.notifyDataSetChanged()
    }

    private fun jumpToWebView(url: String) {
        WebViewActivity.actionStart(context!!, url)
    }

}