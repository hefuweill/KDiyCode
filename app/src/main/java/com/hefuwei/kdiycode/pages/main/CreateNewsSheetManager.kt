package com.hefuwei.kdiycode.pages.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.data.model.Node
import com.hefuwei.kdiycode.pages.choosenode.ChooseNodeActivity
import com.hefuwei.kdiycode.util.UIUtils
import com.hefuwei.kdiycode.views.ActionSheet
import com.hefuwei.kdiycode.views.InputItem

class CreateNewsSheetManager(private val ctx: Activity,
                             private val presenter: MainPresenter) : View.OnClickListener {

    
    @BindView(R.id.tv_submit)
    lateinit var tvSubmit: TextView
    @BindView(R.id.input_title)
    lateinit var inputTitle: InputItem
    @BindView(R.id.input_url)
    lateinit var inputUrl: InputItem
    @BindView(R.id.input_node)
    lateinit var inputNode: InputItem
    private lateinit var sheet: ActionSheet
    private var node: Node? = null

    fun createNews() {
        node = null
        val rootView = View.inflate(ctx, R.layout.actionsheet_createnews, null)
        ButterKnife.bind(this, rootView)
        inputNode.setOnClickListener(this)
        tvSubmit.setOnClickListener(this)
        inputTitle.setFocus()
        sheet = ActionSheet(ctx)
        sheet.setContentView(rootView)
        sheet.show()
        inputTitle.post {
            val inputMethodManager = ctx.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            inputMethodManager.showSoftInput(inputTitle.et, InputMethodManager.SHOW_FORCED)
        }
    }
    
    fun dismiss() {
        sheet.dismiss()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_CHOOSE_NODE && data != null) {
            node = data.getSerializableExtra(ChooseNodeActivity.NODE) as Node
            inputNode.content = node!!.name
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.input_node -> ChooseNodeActivity.actionStart(ctx, node)
            R.id.tv_submit -> realCreateNews()
        }
    }

    private fun realCreateNews() {
        val title = inputTitle.content
        val url = inputUrl.content
        if (TextUtils.isEmpty(title)) {
            UIUtils.showShortToast(R.string.required_news_title)
            return
        }
        if (TextUtils.isEmpty(url) || !url.matches(URL_REGEX.toRegex())) {
            UIUtils.showShortToast(R.string.required_news_url)
            return
        }
        if (node == null) {
            UIUtils.showShortToast(R.string.required_news_node)
            return
        }
        presenter.createNews(title, url, node?.id!!)
    }

    companion object {

        const val RC_CHOOSE_NODE = 0x1
        const val URL_REGEX = "\\w+://\\w+.*"
    }
}