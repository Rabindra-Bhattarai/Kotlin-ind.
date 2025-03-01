package com.example.mykotlin.repository

import android.content.Context
import android.net.Uri
import com.example.mykotlin.model.AddMembersModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddMembersRepositoryImpl: AddMembersRepository {


    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference: DatabaseReference= database.reference.child("AddMembers")




    override fun addMembers(addMembersModel: AddMembersModel, callback: (Boolean, String) -> Unit
    ) {
        var id = reference.push().key.toString()
        addMembersModel.membersId = id
        reference.child(id).setValue(addMembersModel)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Product Added succesfully")
                } else {
                    callback(false, "${it.exception?.message}")
                }
            }
    }

    override fun updateMembers(
        membersId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {

        reference.child(membersId).updateChildren(data)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Product Updated succesfully")
                } else {
                    callback(false, "${it.exception?.message}")
                }
            }

    }

    override fun deleteMembers(
        membersId: String,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(membersId).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Product deleted succesfully")
                } else {
                    callback(false, "${it.exception?.message}")
                }
            }

    }

    override fun getMembersById(
        membersId: String,
        callback: (AddMembersModel?, Boolean, String) -> Unit
    ) {
        reference.child(membersId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var model = snapshot.getValue(AddMembersModel::class.java)
                        callback(model, true, "Data fetched")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null, false, error.message.toString())
                }

            })
    }

    override fun getAllMembers(callback: (List<AddMembersModel>?, Boolean, String) -> Unit) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var members= mutableListOf<AddMembersModel>()
                if (snapshot.exists()) {
                    for (eachMembers in snapshot.children) {
                        var model = eachMembers.getValue(AddMembersModel::class.java)
                        if (model != null) {
                            members.add(model)
                        }
                    }
                    callback(members, true, "fetched")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message.toString())
            }
        })
    }

    override fun uploadImage(
        context: Context,
        imageUri: Uri,
        callback: (String?) -> Unit
    ) {

    }

    override fun getFileNameFromUri(
        context: Context,
        uri: Uri
    ): String? {
        TODO("Not yet implemented")
    }
}