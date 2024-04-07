package com.example.virtualdressup2
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myapp.revery.Garment
import com.myapp.revery.ReveryAIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TryOnFragment : Fragment() {

    private lateinit var reveryAIClient: ReveryAIClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_try_on, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ReveryAI client
        reveryAIClient = ReveryAIClient()

        // Make API call to get and display the outfit
        fetchAndDisplayOutfit()
    }

    private fun fetchAndDisplayOutfit() {
        // Make API call to get filtered garments (outfits)
        GlobalScope.launch(Dispatchers.Main) {
            val filteredGarmentsResponse = reveryAIClient.getFilteredGarments()
            if (filteredGarmentsResponse.success) {
                // Display the fetched outfit (you may need to implement a custom view or RecyclerView)
            } else {
                // Handle API call failure
            }
        }
    }
}