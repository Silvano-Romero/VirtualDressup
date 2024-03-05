package com.example.virtualdressup2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CalendarActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabDelete: FloatingActionButton
    private lateinit var fabBack: FloatingActionButton
    private lateinit var stringDateSelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        calendarView = findViewById(R.id.calendarView)
        fabAdd = findViewById(R.id.fab_add)
        fabDelete = findViewById(R.id.fab_delete)
        fabBack = findViewById(R.id.backButton)

        fabBack.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            stringDateSelected = "$year${month + 1}$dayOfMonth"
        }

        fabAdd.setOnClickListener {
            // Add data to the Firebase database
            var dialog = CalendarDialogFragment()
            dialog.show(supportFragmentManager, "calenderDialog")
        }

        fabDelete.setOnClickListener {
            // Delete outfit or perform any other action
        }
    }
}




