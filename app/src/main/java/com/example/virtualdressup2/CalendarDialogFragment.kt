package com.example.virtualdressup2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualdressup2.databinding.FragmentCalendarDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.myapp.firebase.DAO
import com.myapp.firebase.YourDAO
import kotlinx.coroutines.runBlocking

// This interface defines a contract for handling item click events in the RecyclerView
interface OnItemClickListener {
    fun onItemClick(outfit: RecyclerItem)
}

class CalendarDialogFragment : DialogFragment() {
    private var _binding: FragmentCalendarDialogBinding? = null
    private val binding get() = _binding!!

    // List of outfits to display in the RecyclerView
    private val outfitList = arrayListOf(
        RecyclerItem(R.drawable.outfit1, "Outfit 1"),
        RecyclerItem(R.drawable.outfit2, "Outfit 2"),
        RecyclerItem(R.drawable.outfit3, "Outfit 3"),
        RecyclerItem(R.drawable.outfit4, "Outfit 4"),
        RecyclerItem(R.drawable.outfit5, "Outfit 5"),
        RecyclerItem(R.drawable.outfit6, "Outfit 6")
    )

    // Inflate the layout for the dialog fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Initialize the RecyclerView and set up event listeners
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listener for the cancel button to dismiss the dialog
        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        // Set click listener for the submit button to display the selected outfit
        binding.submitButton.setOnClickListener {
            val selectedOutfit = getSelectedOutfit()
            if (selectedOutfit != null) {
                // Create a map representing the outfit data
                val outfitData = hashMapOf(
                    "imageResource" to selectedOutfit.titleImage,
                    "heading" to selectedOutfit.heading
                )

                // Instantiate your DAO class
                val dao = YourDAO()
                val user = FirebaseAuth.getInstance().currentUser
                val userId = user?.uid


                // Call the method to write the outfit data to your Firestore collection
                try {
                    runBlocking {
                        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "default_user_id"
                        dao.writeDocumentToCollection("Profiles", userId, outfitData)
                    }
                    Toast.makeText(
                        context,
                        "Outfit stored successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Error storing outfit: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(context, "Please select an outfit", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up the RecyclerView with a LinearLayoutManager
        binding.outfitRecyclerView.layoutManager = LinearLayoutManager(context)
        // Create an adapter for the RecyclerView and set the item click listener
        val adapter = OutfitAdapter(outfitList) { onItemClick(it) }
        binding.outfitRecyclerView.adapter = adapter
    }

    // Clean up references to views when the fragment is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Handle item click event from the RecyclerView
    private fun onItemClick(outfit: RecyclerItem) {
        Toast.makeText(context, "Selected outfit: ${outfit.heading}", Toast.LENGTH_SHORT).show()
    }

    // Get the selected outfit from the RecyclerView
    private fun getSelectedOutfit(): RecyclerItem? {
        val selectedPosition = (binding.outfitRecyclerView.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
        return outfitList.getOrNull(selectedPosition)
    }
}
