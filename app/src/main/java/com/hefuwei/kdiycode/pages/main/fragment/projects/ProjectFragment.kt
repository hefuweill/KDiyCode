package com.hefuwei.kdiycode.pages.main.fragment.projects

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity

class ProjectFragment : BaseActivity() {

    @BindView(R.id.sr)
    lateinit var sr: SwipeRefreshLayout
    @BindView(R.id.rv)
    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_project)
    }


}