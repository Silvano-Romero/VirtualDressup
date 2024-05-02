package com.example.virtualdressup2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualdressup2.databinding.FragmentCalendarDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.myapp.firebase.Avatar
import com.myapp.firebase.revery.AvatarDAO
import kotlinx.coroutines.launch

class CalendarDialogFragment : DialogFragment() {
    private var _binding: FragmentCalendarDialogBinding? = null
    private lateinit var outfitAdapter: OutfitAdapter
    private var mostRecentPosition: Int = 0
    private val binding get() = _binding!!
    private val outfitList = mutableListOf<RecyclerItem>()

    private val profileID = FirebaseAuth.getInstance().currentUser?.uid as String



    // Callback interface for outfit selection
    interface OnOutfitSelectedListener {
        fun onOutfitSelected(outfit: RecyclerItem)
    }

    // Listener for outfit selection
    var outfitSelectedListener: OnOutfitSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.submitButton.setOnClickListener {
            val selectedOutfit = getSelectedOutfit()
            if (selectedOutfit != null) {
                // Notify the listener about the selected outfit
                outfitSelectedListener?.onOutfitSelected(selectedOutfit)
                dismiss()
            } else {
                Toast.makeText(context, "Please select an outfit", Toast.LENGTH_SHORT).show()
            }
        }

        binding.outfitRecyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val avatarID = "87463ae7-5ced"
                val avatar: Avatar = AvatarDAO().getSpecificAvatarFromProfile(profileID, avatarID)
                val avatarOutfits = avatar.outfits

                for (outfit in avatarOutfits) {
                    val tryOnImgURL = "https://media.revery.ai/generated_model_image/${outfit.modelFile}.png"
                    outfitList.add(RecyclerItem(R.drawable.outfit1, outfit.outfitID, titleImageURL = tryOnImgURL))
                }

                outfitAdapter = OutfitAdapter(outfitList) { outfit, position ->
                    // Display a toast message indicating the clicked outfit's modelFile
                    mostRecentPosition = position
                    Toast.makeText(
                        requireContext(),
                        "Outfit: ${outfit.heading}\nModel File: ${outfit.titleImageURL}",
                        Toast.LENGTH_LONG
                    ).show()
                }


                binding.outfitRecyclerView.adapter = outfitAdapter
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading outfits: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getSelectedOutfit(): RecyclerItem? {
//        val layoutManager = binding.outfitRecyclerView.layoutManager as LinearLayoutManager
        val selectedPosition = mostRecentPosition
//        println("SELECTED_POSITION: $selectedPosition")
        return outfitAdapter.getItemAtPosition(selectedPosition)
    }


}