package com.example.mykotlin.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mykotlin.R
import com.example.mykotlin.databinding.ActivityRegisterBinding
import com.example.mykotlin.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference : DatabaseReference = database.reference.
    child("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener{
            var email: String=binding.editEmail.text.toString()
            var password: String=binding.editPassword.text.toString()
            var username:String=binding.editRegisterUsername.text.toString()
            var address: String=binding.editAddress.text.toString()

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if (it.isSuccessful){

                    val userId=auth.currentUser?.uid
                    val userModel= UserModel(

                        username, email, address
                    )
                    reference.child(userId.toString()).setValue(userModel)
                        .addOnCompleteListener{
                            if(it.isSuccessful) {
                                Toast.makeText(this@RegisterActivity, "Registration success",
                                    Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@RegisterActivity, it.exception?.message, Toast.LENGTH_SHORT) .show()
                            }
                        }

                    Toast.makeText(this@RegisterActivity,
                        it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}