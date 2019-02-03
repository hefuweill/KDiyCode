package com.hefuwei.kdiycode.data.model

data class LoginModel(val access_token:String, val refresh_token: String,
    val token_type: String, val created_at: Int, val expires_in: Int, val uid: Int) {

    /**
     * access_token : 8447ff97-9b8c-4224-9cec-63b97d34ba65
     * refresh_token : 8447ff97-9b8c-4224-9cec
     * token_type : bearer
     * expires_in : 43199
     * uid : 12
     */
}