package com.example.marvelbrowser

import java.security.MessageDigest

fun String.md5(): ByteArray = MessageDigest.getInstance("MD5").digest(this.toByteArray())

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
