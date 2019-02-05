package com.hefuwei.kdiycode.pages.choosenode

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.data.model.Node
import com.hefuwei.kdiycode.pages.main.CreateNewsSheetManager
import com.hefuwei.kdiycode.util.UIUtils

class ChooseNodeActivity : BaseActivity(), ChooseNodeContract.View,
        RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rg_line1)
    lateinit var rgLine1: RadioGroup
    @BindView(R.id.rg_line2)
    lateinit var rgLine2: RadioGroup
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    private lateinit var nodes: List<Node>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setContentView(R.layout.activity_choose_node)
        presenter = ChooseNodePresenter(this)
    }

    override fun setupEvent() {
        super.setupEvent()
        rgLine1.setOnCheckedChangeListener(this)
        rgLine2.setOnCheckedChangeListener(this)
    }

    override fun setupToolbar() {
        super.setupToolbar()
        toolbar.title = UIUtils.getString(R.string.news_node)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * 由于check方法又会回调onCheckedChange，造成死循环，所以必须先解除注册，调用完再注册
     */
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        rgLine1.setOnCheckedChangeListener(null)
        rgLine2.setOnCheckedChangeListener(null)
        if (group === rgLine1) {
            rgLine2.check(View.NO_ID)
        } else {
            rgLine1.check(View.NO_ID)
        }
        rgLine1.setOnCheckedChangeListener(this)
        rgLine2.setOnCheckedChangeListener(this)
    }

    override fun notifyLoadNodeListSuccess(nodes: List<Node>) {
        this.nodes = nodes
        for (i in 0 until nodes.size / 2) {
            val node = nodes[i]
            val params = LinearLayout.LayoutParams(-1, UIUtils.dp2px(60))
            val rb = RadioButton(this)
            rb.id = i
            rb.text = node.name
            rgLine2.addView(rb, params)
        }
        for (i in nodes.size / 2 until nodes.size) {
            val node = nodes[i]
            val params = LinearLayout.LayoutParams(-1, UIUtils.dp2px(60))
            val rb = RadioButton(this)
            rb.id = i
            rb.text = node.name
            rgLine1.addView(rb, params)
        }
        val selectNode = intent.getSerializableExtra(SELECTED_NODE) as Node?
        if (nodes.contains(selectNode)) {
            val index = nodes.indexOf(selectNode)
            if (index < nodes.size / 2) {
                rgLine2.check(nodes.indexOf(selectNode))
            } else {
                rgLine1.check(nodes.indexOf(selectNode))
            }
        }
    }

    override fun notifyLoadNodeListFail() {
        UIUtils.showShortToast(R.string.load_nodes_list_fail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.choose_node_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.bt_check) {
            var id = rgLine1.checkedRadioButtonId
            if (id == View.NO_ID) {
                id = rgLine2.checkedRadioButtonId
                if (id == View.NO_ID) {
                    UIUtils.showShortToast(R.string.please_choose)
                    return super.onOptionsItemSelected(item)
                }
            }
            val intent = Intent()
            intent.putExtra(NODE, nodes[id])
            setResult(CreateNewsSheetManager.RC_CHOOSE_NODE, intent)
            finish()
        } else if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val NODE = "node"
        const val SELECTED_NODE = "selected_node"

        fun actionStart(ctx: Context, node: Node?) {
            val intent = Intent(ctx, ChooseNodeActivity::class.java)
            intent.putExtra(SELECTED_NODE, node)
            (ctx as Activity).startActivityForResult(intent, CreateNewsSheetManager.RC_CHOOSE_NODE)
        }
    }
}