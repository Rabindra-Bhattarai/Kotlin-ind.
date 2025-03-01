package com.example.mykotlin.repository


import com.google.firebase.auth.FirebaseAuth

class LoginRepoImpl(var auth: FirebaseAuth) : LoginRepo {

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Login successfull")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }
}