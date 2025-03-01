package com.example.mykotlin.repository

interface LoginRepo {
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)
}