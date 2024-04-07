package com.example.virtualdressup2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualdressup2.databinding.FragmentCalendarDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.myapp.firebase.Avatar
import com.myapp.firebase.revery.AvatarDAO
import kotlinx.coroutines.launch

// This interface defines a contract for handling item click events in the RecyclerView
interface OnItemClickListener {
    fun onItemClick(outfit: RecyclerItem)
}

class CalendarDialogFragment : DialogFragment() {
    private var _binding: FragmentCalendarDialogBinding? = null
    private val binding get() = _binding!!

    // List of outfits to display in the RecyclerView
    private val outfitList = mutableListOf<RecyclerItem>()

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
                // Handle submitting the selected outfit (code not provided)
            } else {
                Toast.makeText(context, "Please select an outfit", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up the RecyclerView with a LinearLayoutManager
        binding.outfitRecyclerView.layoutManager = LinearLayoutManager(context)

        // Use lifecycle scope to launch a coroutine
        viewLifecycleOwner.lifecycleScope.launch {
            // Fetch avatar outfits from Firebase and populate the outfitList
            val avatarID = "Avatar01"
            val profileID = "YHd6kmErjjgSoQr9QxgIwA0sUGW2"
            val avatar: Avatar = AvatarDAO().getSpecificAvatarFromProfile(profileID, avatarID)
            val avatarOutfits = avatar.outfits

            // Add outfits to outfitList
            for (outfit in avatarOutfits) {
                outfitList.add(RecyclerItem(R.drawable.female1, outfit.outfitID))
            }

            // Create an adapter for the RecyclerView and set the item click listener
            val adapter = OutfitAdapter(outfitList) { onItemClick(it) }
            binding.outfitRecyclerView.adapter = adapter
        }
    }


    // Clean up references to views when the fragment is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Handle item click event from the RecyclerView
    private fun onItemClick(outfit: RecyclerItem) {
//        Toast.makeText(context, "Selected outfit: ${outfit.outfitID}", Toast.LENGTH_SHORT).show()
    }

    // Get the selected outfit from the RecyclerView
    private fun getSelectedOutfit(): RecyclerItem? {
        val selectedPosition = (binding.outfitRecyclerView.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
        return outfitList.getOrNull(selectedPosition)
    }
}
