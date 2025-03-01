package com.example.mykotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mykotlin.R
import com.example.mykotlin.UserViewModel
import com.example.mykotlin.databinding.ActivityLoginBinding
import com.example.mykotlin.repository.UserRepositoryImpl

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize view binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UserViewModel
        val userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)

        // Handle "Go to Register" button click
        binding.goToRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Handle Login button click
        binding.btnLogin.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()

            // Validate inputs
            if (email.isEmpty()) {
                binding.editEmail.error = "Email is required"
                binding.editEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.editPassword.error = "Password is required"
                binding.editPassword.requestFocus()
                return@setOnClickListener
            }

            // Trigger login logic with callback
            userViewModel.login(email, password) { success, message ->
                if (success) {
                    // Login successful
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    // Redirect to the main activity (DashboardActivity)
                    val intent = Intent(this, DashboardActivity::class.java)  // Adjust to the correct target activity
                    startActivity(intent)
                    finish()  // Close login activity
                } else {
                    // Login failed
                    Toast.makeText(this, "Login failed: $message", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Handle "Forgot Password" text click to navigate to ForgotPasswordActivity
        binding.textViewForgotPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        // Apply window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
