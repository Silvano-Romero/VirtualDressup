package com.example.virtualdressup2

// CalendarActivity.kt

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.virtualdressup2.databinding.ActivityCalendarBinding
import com.squareup.picasso.Picasso

class CalendarActivity : AppCompatActivity(), CalendarDialogFragment.OnOutfitSelectedListener {
    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            Toast.makeText(this, "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
            // Optionally, you can pass the selected date to the dialog fragment
            // to perform specific actions based on the date.
        }

        binding.fabAdd.setOnClickListener {
            val dialog = CalendarDialogFragment()
            dialog.outfitSelectedListener = this
            dialog.show(supportFragmentManager, "calendarDialog")
        }

        // Other initialization code...
    }

    override fun onOutfitSelected(outfit: RecyclerItem) {
        // Handle the selected outfit here
        Toast.makeText(this, "Selected outfit: ${outfit.modelID}", Toast.LENGTH_SHORT).show()
        // You can perform any actions you want with the selected outfit
        // For example, display it in the activity
        displaySelectedOutfit(outfit)
    }

    private fun displaySelectedOutfit(outfit: RecyclerItem) {
        // Update the UI to display the selected outfit details or image
        // For example, if you have an ImageView to display the outfit image:
        binding.outfitImageView.visibility = View.VISIBLE
        bindWithImageURL(outfit)
    }

    private fun bindWithImageURL(outfit: RecyclerItem) {
        // Set view to urlImage
        Picasso.get().load(outfit.titleImageURL).into(binding.outfitImageView)
    }
}


