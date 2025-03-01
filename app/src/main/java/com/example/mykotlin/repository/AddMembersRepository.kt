package com.example.mykotlin.repository

import android.content.Context
import android.net.Uri
import com.example.mykotlin.model.AddMembersModel


interface AddMembersRepository {

//    {
//        "success":true
//        "message": "Product added successfully"
//    }

    fun addMembers(membersModel: AddMembersModel,
                   callback:(Boolean,String) -> Unit
    )
    fun updateMembers(membersId: String,
                      data: MutableMap<String,Any>,
                      callback: (Boolean, String) -> Unit)

    fun deleteMembers(membersId: String,
                      callback: (Boolean, String) -> Unit)

    fun getMembersById(membersId: String,
                       callback: (
                           AddMembersModel?, Boolean,
                           String) -> Unit)

    fun getAllMembers(callback:
                          (List<AddMembersModel>?, Boolean,
                           String) -> Unit)

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri): String?

}