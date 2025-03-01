package com.example.mykotlin.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.model.AddMembersModel
import com.example.mykotlin.repository.AddMembersRepository


class AddMembersViewModel(val repository: AddMembersRepository) {

    fun addMembers(addMembersModel: AddMembersModel,
                   callback:(Boolean,String) -> Unit
    ){
        repository.addMembers(addMembersModel, callback)
    }

    fun updateMembers(membersId:String,
                      data: MutableMap<String,Any>,
                      callback: (Boolean, String) -> Unit){
        repository.updateMembers(membersId, data, callback)
    }

    fun deleteMembers(membersId: String,
                      callback: (Boolean, String) -> Unit){
        repository.deleteMembers(membersId, callback)
    }

    var _members = MutableLiveData<AddMembersModel?>()
    var members = MutableLiveData<AddMembersModel?>()
        get() = _members

    var _allMembers = MutableLiveData<List<AddMembersModel>?>()
    var allMembers = MutableLiveData<List<AddMembersModel>?>()
        get() = _allMembers

    fun getMembersById(membersId: String){
        repository.getMembersById(membersId){
                member,success,message->
            if(success){
                _members.value = member
            }
        }
    }

    var _loadingState = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()
        get() = _loadingState

    fun getAllMember(){
        _loadingState.value = true
        repository.getAllMembers{
                members, success, message ->
            if(success){
//                _allMembers.value = members
                _allMembers.value= members
                _loadingState.value = false
            }
        }
    }
    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit){
        repository.uploadImage(context, imageUri, callback)
    }
}
