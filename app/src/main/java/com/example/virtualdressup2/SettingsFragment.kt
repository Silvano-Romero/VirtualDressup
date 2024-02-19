package com.example.virtualdressup2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var logout: Button
    private lateinit var mTvEmail: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        mTvEmail = view.findViewById(R.id.emailEt)
        logout = view.findViewById(R.id.signOut)
//        fabBack = view.findViewById(R.id.fab_back)

        val currentUser = firebaseAuth.currentUser
        currentUser?.let {
            val email = it.email
            mTvEmail.text = email
        }


        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), SignInActivity::class.java))
        }

        return view
    }

}
