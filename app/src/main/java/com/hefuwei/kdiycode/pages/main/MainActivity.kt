package com.hefuwei.kdiycode.pages.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.hefuwei.kdiycode.Main
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.data.model.UserInfoModel
import com.hefuwei.kdiycode.pages.main.adapter.FragmentAdapter
import com.hefuwei.kdiycode.pages.main.fragment.news.NewsFragment
import com.hefuwei.kdiycode.pages.user.UserProfileActivity
import com.hefuwei.kdiycode.util.UIUtils

class MainActivity : BaseActivity(), MainContract.View {

    @BindView(R.id.vp)
    lateinit var vp: ViewPager
    @BindView(R.id.tab)
    lateinit var tabLayout: TabLayout
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.drawer)
    lateinit var drawer: DrawerLayout
    @BindView(R.id.nav)
    lateinit var nav: NavigationView

    private lateinit var mainPresenter: MainPresenter
    private lateinit var sheetManager: CreateNewsSheetManager
    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        presenter = MainPresenter(this)
        mainPresenter = presenter as MainPresenter
    }

    override fun setupViews() {
        for (i in Main.TABS) {
            tabLayout.addTab(tabLayout.newTab())
            fragments.add(when(i) {
                UIUtils.getString(R.string.news) -> NewsFragment()
                else -> NewsFragment()
            })
        }
        vp.offscreenPageLimit = 2
        vp.adapter = FragmentAdapter(Main.TABS, fragments, supportFragmentManager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setupWithViewPager(vp)
    }

    override fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    override fun setupEvent() {
        nav.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.publish -> {
                    drawer.closeDrawer(Gravity.LEFT)
                    showCreateNewsSheet()
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> drawer.openDrawer(Gravity.LEFT)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun notifyAcquireMeInfoFail() {
        UIUtils.showShortToast(R.string.load_me_fail)
    }

    override fun notifyMeInfoAcquire(info: UserInfoModel) {
        val header = nav.inflateHeaderView(R.layout.nav_header)
        val icAvatar = header.findViewById<ImageView>(R.id.ic_avatar)
        val tvLogin = header.findViewById<TextView>(R.id.tv_login)
        Glide.with(icAvatar).load(UIUtils.replaceLargeAvatarToAvatar(info.avatar_url))
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(icAvatar)
        tvLogin.text = info.login
        icAvatar.setOnClickListener { UserProfileActivity.actionStart(this, info) }
    }

    override fun notifyCreateNewsSuccess() {
        UIUtils.showShortToast(R.string.create_news_success)
        sheetManager.dismiss()
    }

    override fun notifyCreateNewsFail() {
        UIUtils.showShortToast(R.string.create_news_fail)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        sheetManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun showCreateNewsSheet() {
        sheetManager = CreateNewsSheetManager(this, mainPresenter)
        sheetManager.createNews()
    }

    companion object {

        fun actionStart(ctx: Context) {
            val intent = Intent(ctx, MainActivity::class.java)
            ctx.startActivity(intent)
        }
    }
}