package com.example.virtualdressup2

// CalendarActivity.kt
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualdressup2.databinding.ActivityCalendarBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.util.Calendar
import android.util.Log

class CalendarActivity : AppCompatActivity(), CalendarDialogFragment.OnOutfitSelectedListener {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var outfitAdapter: OutfitAdapter
    private lateinit var selectedOutfit: RecyclerItem
    private lateinit var firestore: FirebaseFirestore
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize the outfit adapter with an empty list
        outfitAdapter = OutfitAdapter(emptyList()) { outfit, position ->
            onOutfitSelected(outfit)
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Set the selected date
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = "${year}-${month + 1}-$dayOfMonth"
            Toast.makeText(this, "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
            fetchAndDisplayOutfit(selectedDate!!)
        }

        binding.fabAdd.setOnClickListener {
            val dialog = CalendarDialogFragment()
            dialog.outfitSelectedListener = this
            dialog.show(supportFragmentManager, "calendarDialog")
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this@CalendarActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.fabDelete.setOnClickListener {
            // Check if selectedOutfit is initialized
            if (::selectedOutfit.isInitialized) {
                // Call the removeItem function of the adapter to delete the outfit
                outfitAdapter.removeItem(selectedOutfit)
                // Optionally, perform any additional actions after deletion
                // For example, hide the outfitImageView if needed
                binding.outfitImageView.visibility = View.GONE
            } else {
                Toast.makeText(this, "No outfit selected to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOutfitSelected(outfit: RecyclerItem) {
        selectedOutfit = outfit
        val outfitID = outfit.titleImage
        // Handle the selected outfit here
        Toast.makeText(this, "Selected outfit: $outfitID", Toast.LENGTH_SHORT).show()
        // Save the selected outfit for the selected date
        selectedDate?.let { saveSelectedOutfit(outfit, it) }
        // Display the selected outfit
        displaySelectedOutfit(outfit)
    }

    private fun saveSelectedOutfit(outfit: RecyclerItem, date: String) {
        val outfitData = hashMapOf(
            "outfitImageUrl" to outfit.titleImageURL
            // Add other outfit details if needed
        )

        firestore.collection("calendar")
            .document(date)
            .set(outfitData)
            .addOnSuccessListener {
                Toast.makeText(this, "Outfit saved for $date", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save outfit: $e", Toast.LENGTH_SHORT).show()
            }
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

    private fun fetchAndDisplayOutfit(date: String) {
        firestore.collection("calendar")
            .document(date) // Assuming each date is a document ID
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val outfitImageUrl: String? = documentSnapshot.getString("outfitImageUrl")
                    // Display the outfit image
                    Log.e("Firestore", "Display Outfit Image: $outfitImageUrl")

                    // Create a RecyclerItem with the outfitImageUrl
                    val outfit = RecyclerItem(
                        titleImage = 0, // Set to 0 or any other default value since we're using titleImageURL
                        heading = "", // Add a heading if necessary
                        titleImageURL = outfitImageUrl // Assign outfitImageUrl to titleImageURL
                    )

                    bindWithImageURL(outfit)
                    displaySelectedOutfit(outfit)
                } else {
                    // Clear the displayed outfit image if no outfit is saved for the selected date
                    clearOutfitImage()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error fetching outfit: $e", Toast.LENGTH_SHORT).show()
                Log.e("Firestore", "Error fetching outfit", e)
            }
    }

//    private fun fetchAndDisplayOutfit(date: String) {
//        firestore.collection("calendar")
//            .document(date) // Assuming each date is a document ID
//            .get()
//            .addOnSuccessListener { documentSnapshot ->
//                if (documentSnapshot.exists()) {
//                    // Log the entire documentSnapshot to inspect its contents
//                    Log.d("Firestore", "DocumentSnapshot: $documentSnapshot")
//
//                    // Retrieve the outfitImageUrl field
//                    val outfitImageUrl: String? = documentSnapshot.getString("outfitImageUrl")
//                    // Log the retrieved outfitImageUrl
//                    Log.d("Firestore", "Outfit Image URL: $outfitImageUrl")
//
//                    if (outfitImageUrl != null) {
//                        // Display the outfit image
//                        displayOutfitImage(outfitImageUrl)
//                    } else {
//                        Log.e("Firestore", "OutfitImageUrl is null")
//                        // Clear the displayed outfit image if the outfitImageUrl is null
//                        clearOutfitImage()
//                    }
//                } else {
//                    // Clear the displayed outfit image if no outfit is saved for the selected date
//                    Log.e("Firestore", "Document does not exist for date: $date")
//                    clearOutfitImage()
//                }
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(this, "Error fetching outfit: $e", Toast.LENGTH_SHORT).show()
//                Log.e("Firestore", "Error fetching outfit", e)
//            }
//    }


    private fun displayOutfitImage(imageUrl: String?) {
            // Set view to urlImage
            Picasso.get().load(imageUrl).into(binding.outfitImageView)
    }

    private fun clearOutfitImage() {
        // Clear the displayed outfit image
        binding.outfitImageView.setImageDrawable(null)
    }
}
