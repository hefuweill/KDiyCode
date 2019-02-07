@file: JvmName("Const")
package com.hefuwei.kdiycode

import com.hefuwei.kdiycode.util.UIUtils


class Global {

    companion object {
        const val BASE_URL = "https://diycode.cc/api/v3/"
        const val LOG_TAG = "kDiyCode"
        const val PLATFORM = "android"
        const val CLIENT_ID = "89d9ee3a"
        const val CLIENT_SECRET = "2244a416d0a2fe9b8f73b3823cf9d7312f7c3ea6bc5233e933fd245512515803"
        const val REGISTER_URL = "https://www.diycode.cc/account/sign_up"
    }
}

class Auth {

    companion object {
        const val AUTH_TYPE = "password"
        const val KEY_TOKEN = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
        const val TOKEN = "TOKEN"
        const val CREATE_AT = "CREATE_AT"
        const val EXPIRES_IN = "EXPIRES_IN"
        const val UID = "UID"
        const val USER_NAME = "usernam1"
    }
}

class Main {

    companion object {
        val TABS = arrayOf(UIUtils.getString(R.string.news),
                UIUtils.getString(R.string.projects), UIUtils.getString(R.string.topics))
        const val PAGE_SIZE = 20
    }
}