package com.example.mykotlin.repository

import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordImpl(val auth: FirebaseAuth) : ForgotPassword {
    override fun forgotPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Password reset email sent.")
                } else {
                    callback(false, task.exception?.message ?: "Error occurred.")
                }
            }
    }
}
