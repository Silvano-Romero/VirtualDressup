package com.example.virtualdressup2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualdressup2.databinding.FragmentCalendarDialogBinding

class CalendarDialogFragment : DialogFragment() {
    interface OnItemClickListener {
        fun onItemClick(outfit: RecyclerItem)
    }

    private var _binding: FragmentCalendarDialogBinding? = null
    private val binding get() = _binding!!

    private val outfitList = arrayListOf(
        RecyclerItem(R.drawable.outfit1, "Outfit 1"),
        RecyclerItem(R.drawable.outfit2, "Outfit 2"),
        RecyclerItem(R.drawable.outfit3, "Outfit 3"),
        RecyclerItem(R.drawable.outfit4, "Outfit 4"),
        RecyclerItem(R.drawable.outfit5, "Outfit 5"),
        RecyclerItem(R.drawable.outfit6, "Outfit 6")
    )

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
                Toast.makeText(
                    context,
                    "Selected outfit: ${selectedOutfit.heading}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(context, "Please select an outfit", Toast.LENGTH_SHORT).show()
            }
        }

        binding.outfitRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = OutfitAdapter(outfitList) { onItemClick(it) }
        binding.outfitRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(outfit: RecyclerItem) {
        // Handle item click here
        Toast.makeText(context, "Selected outfit: ${outfit.heading}", Toast.LENGTH_SHORT).show()
    }

    private fun getSelectedOutfit(): RecyclerItem? {
        val selectedPosition = (binding.outfitRecyclerView.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
        return outfitList.getOrNull(selectedPosition)
    }
}
