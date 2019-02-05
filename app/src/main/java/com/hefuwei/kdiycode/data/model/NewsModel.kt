package com.hefuwei.kdiycode.data.model

import java.io.Serializable

data class NewsModel(val updated_at: String? = null, val id: Int = 0,
           val created_at: String? = null, val node_name: String? = null, 
           val last_reply_user_id: Any? = null, val replies_count: Int = 0, 
           val address: String? = null, val replied_at: Any? = null, 
           val last_reply_user_login: Any? = null, val node_id: Int = 0, 
           val user: UserBean, val title: String? = null) {

    class UserBean : Serializable {
        /**
         * id : 2415
         * login : forezp
         * name : 方志朋
         * avatar_url : https://diycode.b0.upaiyun.com/user/large_avatar/2415.jpg
         */

       val id: Int = 0
       val login: String? = null
       val name: String? = null
       val avatar_url: String? = null
    }
}