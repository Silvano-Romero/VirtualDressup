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
import com.google.firebase.auth.FirebaseAuth
import com.myapp.firebase.Avatar
import com.myapp.firebase.revery.AvatarDAO

class CalendarActivity : AppCompatActivity(), CalendarDialogFragment.OnOutfitSelectedListener {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var outfitAdapter: OutfitAdapter
    private lateinit var selectedOutfit: RecyclerItem
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private var selectedDate: String? = null
    private val profileID = FirebaseAuth.getInstance().currentUser?.uid as String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        val profileID = firebaseAuth.currentUser?.uid as String


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
            // Check if selectedDate is not null
            selectedDate?.let { date ->
                // Query the "calendar" collection to check if the selected date exists
                firestore.collection("calendar")
                    .document(date)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            // Date exists, fetch outfit details and initialize selectedOutfit
                            fetchOutfitForDate(date)
                        } else {
                            Toast.makeText(
                                this,
                                "No outfit found for selected date",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error checking date for outfit: $e",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("Firestore", "Error checking date for outfit", e)
                    }
            } ?: run {
                Toast.makeText(this, "No date selected to delete outfit", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchOutfitForDate(date: String) {
        // Fetch outfit details for the given date from Firestore
        firestore.collection("calendar")
            .document(date)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Extract outfit details from the document
                    val outfitImageUrl: String? = documentSnapshot.getString("outfitImageUrl")
                    // Initialize selectedOutfit with fetched outfit details
                    selectedOutfit = RecyclerItem(
                        titleImage = 0, // Set to 0 or any other default value since we're using titleImageURL
                        heading = "", // Add a heading if necessary
                        titleImageURL = outfitImageUrl // Assign outfitImageUrl to titleImageURL
                    )
                    // Call the removeItem function of the adapter to delete the outfit
                    outfitAdapter.removeItem(selectedOutfit)
                    // Delete the outfit document from Firestore
                    deleteOutfitDate(date)
                    // Optionally, perform any additional actions after deletion
                    // For example, hide the outfitImageView if needed
                    binding.outfitImageView.visibility = View.GONE
                } else {
                    Toast.makeText(this, "No outfit found for selected date", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error fetching outfit for date: $e", Toast.LENGTH_SHORT).show()
                Log.e("Firestore", "Error fetching outfit for date", e)
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
            .document(profileID) // Use profileID as the document ID
            .collection(date) // Add a subcollection for each date
            .document("outfit") // Use a document to store the outfit data
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
            .document(profileID) // Use profileID as the document ID
            .collection(date) // Use date as the subcollection
            .document("outfit") // Use "outfit" as the document to store the outfit data
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val outfitImageUrl: String? = documentSnapshot.getString("outfitImageUrl")

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


    private fun deleteOutfitDate(date: String) {
        // Get the document reference for the specified date
        val documentReference = firestore.collection("calendar").document(date)

        // Delete the document from Firestore
        documentReference.delete()
            .addOnSuccessListener {
                // Document successfully deleted
                Toast.makeText(this, "Outfit for $date deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Failed to delete the document
                Toast.makeText(this, "Failed to delete outfit for $date: $e", Toast.LENGTH_SHORT).show()
                Log.e("Firestore", "Error deleting outfit for $date", e)
            }
    }


    private fun clearOutfitImage() {
        // Clear the displayed outfit image
        binding.outfitImageView.setImageDrawable(null)
    }
}
