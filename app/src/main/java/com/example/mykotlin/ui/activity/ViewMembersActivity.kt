package com.example.mykotlin.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykotlin.adapter.MembersAdapter
import com.example.mykotlin.databinding.ActivityViewMembersBinding
import com.example.mykotlin.model.AddMembersModel
import com.google.firebase.database.*

class ViewMembersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewMembersBinding
    private lateinit var database: DatabaseReference
    private lateinit var memberList: ArrayList<AddMembersModel>
    private lateinit var adapter: MembersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMembersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("AddMembers")
        memberList = ArrayList()
        adapter = MembersAdapter(memberList) { member -> deleteMember(member) }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        fetchMembers()
    }

    private fun fetchMembers() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                memberList.clear()
                for (data in snapshot.children) {
                    val member = data.getValue(AddMembersModel::class.java)
                    member?.let { memberList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewMembersActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteMember(member: AddMembersModel) {
        AlertDialog.Builder(this)
            .setTitle("Delete Member")
            .setMessage("Are you sure you want to delete ${member.name}?")
            .setPositiveButton("Yes") { _, _ ->
                member.membersId?.let {
                    database.child(it).removeValue()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Member deleted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to delete member", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}
