package com.hefuwei.kdiycode.pages.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import butterknife.BindView
import com.bumptech.glide.Glide
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.data.model.UserInfoModel
import com.hefuwei.kdiycode.util.UIUtils
import com.hefuwei.kdiycode.views.ProfileItem
import com.hefuwei.kdiycode.views.MarqueeToolbar

/**
 * 用户的个人信息
 */
class UserProfileActivity : BaseActivity(), UserProfileContract.View, View.OnClickListener {

    @BindView(R.id.item_username)
    lateinit var itemUsername: ProfileItem
    @BindView(R.id.item_createAt)
    lateinit var itemCreateAt: ProfileItem
    @BindView(R.id.item_email)
    lateinit var itemEmail: ProfileItem
    @BindView(R.id.item_level)
    lateinit var itemLevel: ProfileItem
    @BindView(R.id.iv)
    lateinit var iv: ImageView
    @BindView(R.id.ll_avatar)
    lateinit var llAvatar: LinearLayout
    @BindView(R.id.toolbar)
    lateinit var toolbar: MarqueeToolbar
    lateinit var model: UserInfoModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userprofile)
    }

    override fun setupViews() {
        super.setupViews()
        model = intent.getSerializableExtra(INFO) as UserInfoModel
        itemUsername.tvContent.text = model.name
        itemEmail.tvContent.text = model.email
        itemCreateAt.tvContent.text = model.created_at
        itemLevel.tvContent.text = model.level_name
        Glide.with(iv).load(UIUtils.replaceLargeAvatarToAvatar(model.avatar_url))
                .into(iv)
    }

    override fun setupToolbar() {
        super.setupToolbar()
        toolbar.title = "${model.name}的个人资料"
        setSupportActionBar(toolbar)
    }

    override fun setupEvent() {
        super.setupEvent()
        itemUsername.setOnClickListener(this)
        itemEmail.setOnClickListener(this)
        itemLevel.setOnClickListener(this)
        itemCreateAt.setOnClickListener(this)
        llAvatar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        UIUtils.showLongToast(R.string.interface_not_support)
    }


    companion object {

        private const val INFO = "info"

        fun actionStart(ctx: Context, userInfo: UserInfoModel) {
            val intent = Intent(ctx, UserProfileActivity::class.java)
            intent.putExtra(INFO, userInfo)
            ctx.startActivity(intent)
        }
    }
}