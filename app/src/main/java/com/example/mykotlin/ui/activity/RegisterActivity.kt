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
import com.example.mykotlin.databinding.ActivityRegisterBinding
import com.example.mykotlin.model.UserModel
import com.example.mykotlin.repository.UserRepositoryImpl
import com.example.mykotlin.utils.LoadingUtils

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)

        // Handle "Register" button click
        binding.btnRegister.setOnClickListener {
            loadingUtils.show()
            val email: String = binding.editEmail.text.toString()
            val password: String = binding.editPassword.text.toString()
            val username: String = binding.editRegisterUsername.text.toString()
            val address: String = binding.editAddress.text.toString()

            userViewModel.signup(email, password) { success, message, userId ->
                if (success) {
                    val userModel = UserModel(
                        userId, username, email, address
                    )
                    addUser(userModel)
                } else {
                    loadingUtils.dismiss()
                    Toast.makeText(
                        this@RegisterActivity,
                        message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Handle Floating Action Button click to go to LoginActivity
        binding.regBack.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        // Apply edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Function to add user to database
    private fun addUser(userModel: UserModel) {
        userViewModel.addUserToDatabase(userModel.userId, userModel) { success, message ->
            if (success) {
                Toast.makeText(
                    this@RegisterActivity, message, Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@RegisterActivity, message, Toast.LENGTH_SHORT
                ).show()
            }
            loadingUtils.dismiss()
        }
    }
}
