package com.hefuwei.kdiycode.pages.splash

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.pages.login.LoginActivity
import com.hefuwei.kdiycode.pages.main.MainActivity
import com.hefuwei.kdiycode.util.UIUtils
import permissions.dispatcher.*

@RuntimePermissions
class SplashActivity: BaseActivity(), SplashContract.View {

    private var isEnterNextPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter = SplashPresenter(this)
    }

    override fun enterNextPage(gotoLogin: Boolean) {
        if (!isEnterNextPage) {
            isEnterNextPage = true
            realEnterNextPageWithPermissionCheck(gotoLogin)
        }
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationaleForWriteExternalStorage(request: PermissionRequest) {
        showRationaleDialog(request)
    }

    /**
     * Maybe in sub Thread
     */
    private fun showRationaleDialog(request: PermissionRequest) {
        runOnUiThread {
            AlertDialog.Builder(this)
                    .setMessage(R.string.need_write_external_storage)
                    .setNegativeButton(R.string.cancel) { _, _ ->  finish() }
                    .setPositiveButton(R.string.toAuth) { _, _ -> request.proceed()}
                    .create()
                    .show()
        }
    }


    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onWriteExternalStorageDeny() {
        UIUtils.showShortToast(R.string.write_external_storage_deny)
        finish()
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onWriteExternalStorageNeverAskAgain() {
        UIUtils.showShortToast(R.string.write_external_storage_not_ask)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun realEnterNextPage(gotoLogin: Boolean) {
        if (gotoLogin) {
            LoginActivity.actionStart(this)
        } else {
            MainActivity.actionStart(this)
        }
        finish()
    }
}