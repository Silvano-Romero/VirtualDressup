package com.example.virtualdressup2

// CalendarDialogFragment.kt

import android.content.Intent
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

class CalendarDialogFragment : DialogFragment() {
    private var _binding: FragmentCalendarDialogBinding? = null
    private val binding get() = _binding!!
    private val outfitList = mutableListOf<RecyclerItem>()

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
                val avatarID = "Avatar01"
                val profileID = "YHd6kmErjjgSoQr9QxgIwA0sUGW2"
                val avatar: Avatar = AvatarDAO().getSpecificAvatarFromProfile(profileID, avatarID)
                val avatarOutfits = avatar.outfits

                for (outfit in avatarOutfits) {
                    val tryOnImgURL = "https://media.revery.ai/generated_model_image/d79b5e0a1b2fd3817da7c3a26005b4b0;${outfit.modelFile};17124436897514586.png"
                    outfitList.add(RecyclerItem(R.drawable.outfit1, outfit.outfitID, titleImageURL = outfit.modelFile))
                }

                val adapter = OutfitAdapter(outfitList) { onItemClick(it) }
                binding.outfitRecyclerView.adapter = adapter
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading outfits: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(outfit: RecyclerItem) {
        // Pass the selected outfit back to the activity or fragment
        outfitSelectedListener?.onOutfitSelected(outfit)
    }

    private fun getSelectedOutfit(): RecyclerItem? {
        val selectedPosition = (binding.outfitRecyclerView.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
        return outfitList.getOrNull(selectedPosition)
    }
}
