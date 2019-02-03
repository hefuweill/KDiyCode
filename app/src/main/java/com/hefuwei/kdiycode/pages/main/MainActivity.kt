package com.hefuwei.kdiycode.pages.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.tabs.TabLayout
import com.hefuwei.kdiycode.Main
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.pages.main.adapter.FragmentAdapter
import com.hefuwei.kdiycode.pages.main.fragment.news.NewsFragment
import com.hefuwei.kdiycode.util.UIUtils

class MainActivity : BaseActivity() {

    @BindView(R.id.vp)
    lateinit var vp: ViewPager
    @BindView(R.id.tab)
    lateinit var tabLayout: TabLayout
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
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

    companion object {

        fun actionStart(ctx: Context) {
            val intent = Intent(ctx, MainActivity::class.java)
            ctx.startActivity(intent)
        }
    }
}