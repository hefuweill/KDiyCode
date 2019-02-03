package com.hefuwei.kdiycode.data.model

import java.io.Serializable

data class UserInfoModel(val id: Int, val login: String, val name: String, val avatar_url: String,
                         val location: Any, val company: Any, val twitter: Any, var website: Any,
                         val bio: Any, val tagline: Any, val github: Any, val created_at: String,
                         val email: String, val topics_count: Int, val replies_count: Int,
                         val following_count: Int, val followers_count: Int, val favorites_count: Int,
                         val level: String, val level_name: String) : Serializable {
}