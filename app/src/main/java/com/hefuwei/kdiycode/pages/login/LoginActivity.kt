package com.hefuwei.kdiycode.pages.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textfield.TextInputLayout
import com.hefuwei.kdiycode.Global
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.pages.main.MainActivity
import com.hefuwei.kdiycode.pages.web.WebViewActivity
import com.hefuwei.kdiycode.util.UIUtils

class LoginActivity : BaseActivity(), LoginContract.View {

    @BindView(R.id.til_username)
    lateinit var tilUsername: TextInputLayout
    @BindView(R.id.til_password)
    lateinit var tilPassword: TextInputLayout
    @BindView(R.id.bt_login)
    lateinit var btLogin: Button
    @BindView(R.id.tv_register)
    lateinit var tvRegister: TextView
    @BindView(R.id.topPlaceHolder)
    lateinit var topPlaceHolder: View
    @BindView(R.id.middlePlaceHolder)
    lateinit var middlePlaceHolder: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
    }

    override fun setupEvent() {
        btLogin.setOnClickListener {
            hideKeyBoard()
            val name = tilUsername.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()
            (presenter as LoginContract.Presenter).login(name, password)
        }
        tvRegister.setOnClickListener { WebViewActivity.actionStart(this, Global.REGISTER_URL) }
        tilUsername.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    showNameError()
                } else {
                    resetUserEdit()
                }
            }
        })
        tilPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    showPasswordError()
                } else {
                    resetPasswordEdit()
                }
            }
        })
    }

    override fun enterNextPage() {
        UIUtils.showShortToast(R.string.login_success)
        MainActivity.actionStart(this)
        finish()
    }

    override fun showNameError() {
        tilUsername.isErrorEnabled = true
        tilUsername.error = UIUtils.getString(R.string.username_null)
    }

    override fun showPasswordError() {
        tilPassword.isErrorEnabled = true
        tilPassword.error = UIUtils.getString(R.string.password_null)
    }

    override fun onLoginFail(msg: String?) {
        UIUtils.showShortToast(R.string.name_or_password)
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    fun resetUserEdit() {
        tilUsername.isErrorEnabled = false
    }

    fun resetPasswordEdit() {
        tilPassword.isErrorEnabled = false
    }


    companion object {

        /**
         * 不允许传入Application，会因为找不到栈而报错
         */
        fun actionStart(ctx: Context) {
            val intent = Intent(ctx, LoginActivity::class.java)
            ctx.startActivity(intent)
        }
    }
}