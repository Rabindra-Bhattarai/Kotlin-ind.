package com.example.mykotlin.repository

import android.util.Log
import com.example.mykotlin.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserRepositoryImpl : UserRepository {
    private val auth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Login Successful")
                } else {
                    callback(false, task.exception?.message ?: "Error occurred.")
                }
            }
    }

    override fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "Signup Successful", auth.currentUser ?.uid ?: "")
            } else {
                // Log the error message for debugging
                val errorMessage = task.exception?.message ?: "Error occurred."
                Log.e("SignupError", errorMessage)
                callback(false, "Signup Failed", errorMessage)
            }
        }
    }

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

    override fun addUserToDatabase(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit) {
        // Add user to Firebase database (implementation depends on your Firebase setup)
        callback(true, "User added to database")
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun getUserDataFromDatabase(userId: String, callback: (UserModel?, Boolean, String) -> Unit) {
        // Fetch user data from the Firebase database (implementation depends on your Firebase setup)
        callback(null, true, "User data fetched successfully")
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        auth.signOut()
        callback(true, "Logged out successfully")
    }

    override fun editProfile(userId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        // Edit user profile in Firebase database (implementation depends on your Firebase setup)
        callback(true, "Profile updated successfully")
    }
}
