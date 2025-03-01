package com.example.mykotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlin.R
import com.example.mykotlin.model.AddMembersModel

class MembersAdapter(
    private val membersList: ArrayList<AddMembersModel>,
    private val onDelete: (AddMembersModel) -> Unit
) : RecyclerView.Adapter<MembersAdapter.MembersViewHolder>() {

    class MembersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete) // Delete button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_member, parent, false)
        return MembersViewHolder(view)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        val member = membersList[position]
        holder.tvName.text = member.name
        holder.tvAddress.text = "Address: ${member.address}"
        holder.tvPhone.text = "Phone: ${member.phone}"
        holder.tvEmail.text = "Email: ${member.email}"

        // Handle delete button click
        holder.btnDelete.setOnClickListener {
            onDelete(member) // Call delete function
        }
    }

    override fun getItemCount(): Int = membersList.size
}
