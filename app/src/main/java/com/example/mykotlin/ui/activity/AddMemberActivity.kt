package com.example.mykotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlin.R
import com.example.mykotlin.databinding.ActivityAddMemberBinding
import com.example.mykotlin.model.AddMembersModel
import com.example.mykotlin.repository.AddMembersRepositoryImpl
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddMemberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMemberBinding
    private val repository = AddMembersRepositoryImpl()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.reference.child("AddMembers")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegister.setOnClickListener {
            registerMember()
        }
        binding.btnLogout.setOnClickListener {
            // Navigate to LoginActivity and finish current activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerMember() {
        val name = binding.etName.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        if (name.isNotEmpty() && address.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
            val memberId = reference.push().key // Generate a unique ID

            if (memberId != null) {
                val member = AddMembersModel(
                    membersId = memberId,
                    name = name,
                    address = address,
                    phone = phone,
                    email = email
                )

                reference.child(memberId).setValue(member)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Member registered successfully!", Toast.LENGTH_SHORT).show()
                        clearFields()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to register member", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Error generating Member ID", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }


    private fun clearFields() {
        binding.etName.text.clear()
        binding.etAddress.text.clear()
        binding.etPhone.text.clear()
        binding.etEmail.text.clear()
    }
}
