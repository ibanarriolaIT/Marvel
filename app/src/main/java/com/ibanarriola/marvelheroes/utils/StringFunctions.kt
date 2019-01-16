package com.ibanarriola.marvelheroes.utils

import java.security.MessageDigest

fun String.generateHash(): String {
    val md = MessageDigest.getInstance("MD5")
    val digested = md.digest(this.toByteArray())
    return digested.joinToString("") {
        String.format("%02x", it)
    }
}