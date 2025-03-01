package com.example.mykotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.example.mykotlin.R
import com.example.mykotlin.ui.fragment.ProfileFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var fragmentContainer: FrameLayout
    private lateinit var profileButton: View
    private lateinit var mainContent: View
    private lateinit var logoutButton: View
    private lateinit var memberRegister: CardView
    private lateinit var viewMemberList: CardView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        fragmentContainer = findViewById(R.id.fragmentContainer)
        profileButton = findViewById(R.id.btnProfile)
        mainContent = findViewById(R.id.mainContent)
        logoutButton = findViewById(R.id.btnLogout)
        memberRegister = findViewById(R.id.memberRegister)
        viewMemberList = findViewById(R.id.viewMemberList)


        profileButton.setOnClickListener {

            mainContent.visibility = View.GONE
            fragmentContainer.visibility = View.VISIBLE

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ProfileFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }

        logoutButton.setOnClickListener {
            // Create an Intent to navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Set the click listener for memberRegister CardView
        memberRegister.setOnClickListener {
            // Create an Intent to navigate to AddMemberActivity
            val intent = Intent(this, AddMemberActivity::class.java)
            startActivity(intent)
        }

        viewMemberList.setOnClickListener {
            val intent = Intent(this, ViewMembersActivity::class.java)
            startActivity(intent)
        }

    }
}
