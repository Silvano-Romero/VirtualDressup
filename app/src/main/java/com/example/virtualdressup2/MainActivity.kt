package com.example.virtualdressup2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.virtualdressup2.databinding.ActivityMainBinding
import com.example.virtualdressup2.databinding.ActivitySignUpBinding
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        finish()
    }
}