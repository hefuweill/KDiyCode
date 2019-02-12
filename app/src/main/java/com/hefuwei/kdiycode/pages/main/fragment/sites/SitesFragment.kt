package com.hefuwei.kdiycode.pages.main.fragment.sites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseFragment
import com.hefuwei.kdiycode.pages.main.fragment.projects.SitesContract
import com.hefuwei.kdiycode.pages.web.WebViewActivity

class SitesFragment : BaseFragment(), SitesContract.View {

    @BindView(R.id.sr)
    lateinit var sr: SwipeRefreshLayout
    @BindView(R.id.rv)
    lateinit var rv: RecyclerView
    lateinit var adapter: SitesAdapter
    private lateinit var sitesPresenter: SitesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_sites, container, false)
        presenter = SitesPresenter(this)
        sitesPresenter = presenter as SitesPresenter
        return rootView
    }

    override fun setupView() {
        super.setupView()
        sr.isRefreshing = true
        adapter = SitesAdapter(R.layout.item_site, R.layout.item_site_header, sitesPresenter.dataList)
        rv.layoutManager = GridLayoutManager(context, 2)
        rv.adapter = adapter
    }

    override fun setupEvent() {
        super.setupEvent()
        adapter.setOnItemClickListener { _, _, position ->
            val model = sitesPresenter.dataList[position]
            if (!model.isHeader) {
                WebViewActivity.actionStart(context!!, model.t.url!!)
            }
        }
        sr.setOnRefreshListener { sitesPresenter.onRefresh() }
    }

    override fun notifyDataSetChanged() {
        sr.isRefreshing = false
        adapter.notifyDataSetChanged()
    }

    override fun notifyDataLoadFail() {

    }
}