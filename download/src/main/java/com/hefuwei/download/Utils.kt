package com.hefuwei.download

import java.math.BigInteger
import java.security.MessageDigest

class MD5 {

    companion object {
        fun md5(text: String): String {
            val md = MessageDigest.getInstance("MD5")
            md.update(text.toByteArray())
            return BigInteger(1, md.digest()).toString(16)
        }
    }

}