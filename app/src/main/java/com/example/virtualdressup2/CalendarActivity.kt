package com.example.virtualdressup2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CalendarActivity : AppCompatActivity() {
    // Declare properties for views and data
    private lateinit var calendarView: CalendarView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabDelete: FloatingActionButton
    private lateinit var fabBack: FloatingActionButton
    private lateinit var stringDateSelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar) // Set the layout for this activity

        // Initialize views by finding their corresponding views in the layout
        calendarView = findViewById(R.id.calendarView)
        fabAdd = findViewById(R.id.fab_add)
        fabDelete = findViewById(R.id.fab_delete)
        fabBack = findViewById(R.id.backButton)

        // Set click listener for the back button to navigate to the MainActivity
        fabBack.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Set a listener to detect date changes in the calendar view
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            stringDateSelected = "$year${month + 1}$dayOfMonth"
        }

        // Set click listener for the "Add" floating action button
        fabAdd.setOnClickListener {
            // When clicked, create and show a CalendarDialogFragment to add data
            var dialog = CalendarDialogFragment()
            dialog.show(supportFragmentManager, "calenderDialog")
        }

        // Set click listener for the "Delete" floating action button
        fabDelete.setOnClickListener {
            // Placeholder for handling delete action, not implemented in this code snippet
        }
    }
}
