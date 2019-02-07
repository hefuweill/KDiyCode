package com.hefuwei.kdiycode.pages.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.util.UIUtils

/**
 * 该App，所有webView的承载类
 */
class WebViewActivity: BaseActivity(), WebViewContract.View {

    @BindView(R.id.wv)
    lateinit var webView: WebView
    @BindView(R.id.pb)
    lateinit var pb: ProgressBar
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        ButterKnife.bind(this)
        presenter = WebViewPresenter(this)
    }

    override fun setupViews() {
        webView.loadUrl(intent.getStringExtra(URL))
    }

    override fun setupToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setupEvent() {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                pb.progress = newProgress
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                toolbar.title = title
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                pb.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                pb.visibility = View.GONE
            }
        }
        webView.settings.javaScriptEnabled = true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_webview, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> finish()
            R.id.favorite -> (presenter as WebViewPresenter).addMyFavorite(webView.title, webView.url)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onAddFavoriteSuccess() {
        UIUtils.showShortToast(R.string.save_success)
    }

    companion object {

        private const val URL = "url"

        fun actionStart(ctx: Context, url: String) {
            val intent = Intent(ctx, WebViewActivity::class.java)
            intent.putExtra(URL, url)
            ctx.startActivity(intent)
        }
    }
}