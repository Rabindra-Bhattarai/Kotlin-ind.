package com.example.mykotlin.repository

interface ForgotPassword {
    fun forgotPassword(email: String, callback: (Boolean, String) -> Unit)
}