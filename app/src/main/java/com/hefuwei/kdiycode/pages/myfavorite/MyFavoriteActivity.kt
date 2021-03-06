package com.hefuwei.kdiycode.pages.myfavorite

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.hefuwei.kdiycode.Global
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.pages.web.WebViewActivity
import com.hefuwei.kdiycode.util.ShareUtils
import com.hefuwei.kdiycode.util.UIUtils

/**
 * 个人收藏界面，本地实现
 */
class MyFavoriteActivity : BaseActivity(), MyFavoriteContract.View {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.rv)
    lateinit var rv: RecyclerView
    private lateinit var adapter: MyFavoriteAdapter
    private lateinit var favoritePresenter: MyFavoritePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfavorite)
        presenter = MyFavoritePresenter(this)
        favoritePresenter = presenter as MyFavoritePresenter
    }

    override fun setupViews() {
        super.setupViews()
        adapter =  MyFavoriteAdapter(R.layout.item_myfavorite, favoritePresenter.favorites)
        adapter.emptyView = View.inflate(this, R.layout.pager_empty, null)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(MItemTouchCallback(object : MItemTouchCallback.OnItemChangeListener {
            override fun onItemRemove(position: Int) {
                (presenter as MyFavoritePresenter).deleteFavorite(position)
            }
        }))
        itemTouchHelper.attachToRecyclerView(rv)
    }

    override fun onStart() {
        super.onStart()
        if (isCreated) {
            // 这个页面需要每次显示都读取下数据
            presenter?.subscribe()
        }
    }

    override fun setupToolbar() {
        toolbar.title = UIUtils.getString(R.string.myFavorite)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun setupEvent() {
        super.setupEvent()
        adapter.setOnItemClickListener { _, _, position ->
            WebViewActivity.actionStart(this, favoritePresenter.favorites[position].url)
        }
        adapter.setOnItemLongClickListener { _, _, position ->
            val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipBoard.text = (presenter as MyFavoritePresenter).favorites[position].url
            UIUtils.showShortToast(getString(R.string.copy_success))
            true
        }
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
        val guided = ShareUtils.read(Global.FAVORITE, false)
        if (!guided && favoritePresenter.favorites.size > 0) {
            rv.post { FavoriteGuideDialog(this, rv.getChildAt(0))
                    .show() }
            ShareUtils.save(Global.FAVORITE, true)
        }
    }

    override fun notifyDeleteResult(isSuccess: Boolean) = if (isSuccess) {
        UIUtils.showShortToast(getString(R.string.delete_success))
        adapter.notifyDataSetChanged()
    } else {
        UIUtils.showShortToast(getString(R.string.delete_fail))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun actionStart(ctx: Context) {
            val intent = Intent(ctx, MyFavoriteActivity::class.java)
            ctx.startActivity(intent)
        }
    }

}