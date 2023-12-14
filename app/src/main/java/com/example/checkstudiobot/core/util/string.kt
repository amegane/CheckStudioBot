package com.example.checkstudiobot.core.util

fun generateRandomString(length: Int): String {
    val strings = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
    return (1 .. length).map { strings.random() }.joinToString("")
}